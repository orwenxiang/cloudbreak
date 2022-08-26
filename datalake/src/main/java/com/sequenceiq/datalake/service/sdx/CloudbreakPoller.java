package com.sequenceiq.datalake.service.sdx;

import static com.sequenceiq.datalake.service.sdx.flowcheck.FlowState.FAILED;
import static com.sequenceiq.datalake.service.sdx.flowcheck.FlowState.FINISHED;
import static com.sequenceiq.datalake.service.sdx.flowcheck.FlowState.RUNNING;
import static com.sequenceiq.datalake.service.sdx.flowcheck.FlowState.UNKNOWN;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dyngr.Polling;
import com.dyngr.core.AttemptResult;
import com.dyngr.core.AttemptResults;
import com.google.common.collect.Sets;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.StackV4Endpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.StackStatusV4Response;
import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.auth.crn.RegionAwareInternalCrnGeneratorFactory;
import com.sequenceiq.cloudbreak.cloud.scheduler.PollGroup;
import com.sequenceiq.datalake.entity.SdxCluster;
import com.sequenceiq.datalake.flow.statestore.DatalakeInMemoryStateStore;
import com.sequenceiq.datalake.service.sdx.flowcheck.CloudbreakFlowService;
import com.sequenceiq.datalake.service.sdx.flowcheck.FlowState;
import com.sequenceiq.datalake.service.sdx.status.SdxStatusService;

@Component
public class CloudbreakPoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudbreakPoller.class);

    @Inject
    private CloudbreakFlowService cloudbreakFlowService;

    @Inject
    private StackV4Endpoint stackV4Endpoint;

    @Inject
    private SdxStatusService sdxStatusService;

    @Inject
    private RegionAwareInternalCrnGeneratorFactory regionAwareInternalCrnGeneratorFactory;

    public void pollCreateUntilAvailable(SdxCluster sdxCluster, PollingConfig pollingConfig) {
        waitForState("Data Lake creation", sdxCluster, pollingConfig,
                Status.getAvailableStatuses(), Sets.immutableEnumSet(Status.CREATE_FAILED));
    }

    public void pollCreateUntilVerticalScaleDone(SdxCluster sdxCluster, PollingConfig pollingConfig) {
        Set<Status> acceptedStatus = new HashSet<>();
        acceptedStatus.addAll(Status.getAvailableStatuses());
        acceptedStatus.addAll(Status.getStoppedStatuses());

        waitForState("Data Lake vertical scale", sdxCluster, pollingConfig,
                acceptedStatus, Sets.immutableEnumSet(Status.getUnschedulableStatuses()));
    }

    public void pollUpdateUntilAvailable(String process, SdxCluster sdxCluster, PollingConfig pollingConfig) {
        waitForState(process, sdxCluster, pollingConfig,
                Status.getAvailableStatuses(), Sets.immutableEnumSet(Status.UPDATE_FAILED));
    }

    public void pollStartUntilAvailable(SdxCluster sdxCluster, PollingConfig pollingConfig) {
        waitForState("Start", sdxCluster, pollingConfig,
                Status.getAvailableStatuses(), Sets.immutableEnumSet(Status.START_FAILED));
    }

    public void pollStopUntilStopped(SdxCluster sdxCluster, PollingConfig pollingConfig) {
        waitForState("Stop", sdxCluster, pollingConfig,
                Sets.immutableEnumSet(Status.STOPPED), Sets.immutableEnumSet(Status.STOP_FAILED));
    }

    public void pollCcmUpgradeUntilAvailable(SdxCluster sdxCluster, PollingConfig pollingConfig) {
        waitForState("CCM upgrade", sdxCluster, pollingConfig,
                Status.getAvailableStatuses(), Sets.immutableEnumSet(Status.UPGRADE_CCM_FAILED));
    }

    public void pollDatabaseServerUpgradeUntilAvailable(SdxCluster sdxCluster, PollingConfig pollingConfig) {
        waitForState("Database server upgrade", sdxCluster, pollingConfig,
                Status.getAvailableStatuses(), Sets.immutableEnumSet(Status.EXTERNAL_DATABASE_UPGRADE_FAILED));
    }

    public void pollFlowStateByFlowIdUntilComplete(String process, String flowId, Long sdxId, PollingConfig pollingConfig) {
        waitForFlowStateByFlowId(process, flowId, sdxId, pollingConfig, Sets.immutableEnumSet(FINISHED), Sets.immutableEnumSet(FAILED, UNKNOWN));
    }

    private void waitForState(
            String process,
            SdxCluster sdxCluster,
            PollingConfig pollingConfig,
            Set<Status> targetStatuses,
            Set<Status> failedStatuses) {
        Polling.waitPeriodly(pollingConfig.getSleepTime(), pollingConfig.getSleepTimeUnit())
                .stopIfException(pollingConfig.getStopPollingIfExceptionOccurred())
                .stopAfterDelay(pollingConfig.getDuration(), pollingConfig.getDurationTimeUnit())
                .run(() -> checkClusterStatus(process, sdxCluster, targetStatuses, failedStatuses));
    }

    private AttemptResult<StackStatusV4Response> checkClusterStatus(
            String process,
            SdxCluster sdxCluster,
            Set<Status> targetStatuses,
            Set<Status> failedStatuses) {
        LOGGER.info("{} polling cloudbreak for stack status: '{}' in '{}' env", process, sdxCluster.getClusterName(), sdxCluster.getEnvName());
        try {
            if (PollGroup.CANCELLED.equals(DatalakeInMemoryStateStore.get(sdxCluster.getId()))) {
                LOGGER.info("{} polling cancelled in inmemory store, id: {}", process, sdxCluster.getId());
                return AttemptResults.breakFor(process + " polling cancelled on '" + sdxCluster.getClusterName() + "' cluster.");
            }
            FlowState flowState = cloudbreakFlowService.getLastKnownFlowState(sdxCluster);
            if (RUNNING.equals(flowState)) {
                LOGGER.info("{} polling will continue, cluster has an active flow in Cloudbreak.", process);
                return AttemptResults.justContinue();
            } else {
                return getStackResponseAttemptResult(process, sdxCluster, flowState, targetStatuses, failedStatuses);
            }
        } catch (NotFoundException e) {
            LOGGER.debug("Stack not found on CB side " + sdxCluster.getClusterName(), e);
            return AttemptResults.breakFor("Stack not found on CB side " + sdxCluster.getClusterName());
        }
    }

    private AttemptResult<StackStatusV4Response> getStackResponseAttemptResult(
            String process,
            SdxCluster sdxCluster,
            FlowState flowState,
            Set<Status> targetStatuses,
            Set<Status> failedStatuses) {
        StackStatusV4Response statusResponse = getStackAndClusterStatusWithInternalActor(sdxCluster);
        LOGGER.info("Response from cloudbreak: {}", statusResponse);
        if (FAILED.equals(flowState)) {
            String message = sdxStatusService.getShortStatusMessage(statusResponse);
            LOGGER.info("{} flow finished, but failed. {}", process, message);
            return failedPolling(process, sdxCluster, message);
        } else if (oneOf(statusResponse.getStatus(), targetStatuses)
                && oneOf(statusResponse.getClusterStatus(), targetStatuses)) {
            return AttemptResults.finishWith(statusResponse);
        } else if (oneOf(statusResponse.getStatus(), failedStatuses)) {
            LOGGER.info("{} failed. Stack is in {} status.", process, statusResponse.getStatus());
            return failedPolling(process, sdxCluster, statusResponse.getStatusReason());
        } else if (oneOf(statusResponse.getClusterStatus(), failedStatuses)) {
            LOGGER.info("{} failed. Cluster is in {} status.", process, statusResponse.getClusterStatus());
            return failedPolling(process, sdxCluster, statusResponse.getClusterStatusReason());
        } else if (FINISHED.equals(flowState)) {
            String message = sdxStatusService.getShortStatusMessage(statusResponse);
            LOGGER.info("{} flow finished, but stack or cluster is not available. {}", process, message);
            return failedPolling(process, sdxCluster, message);
        } else {
            return AttemptResults.justContinue();
        }
    }

    private StackStatusV4Response getStackAndClusterStatusWithInternalActor(SdxCluster sdxCluster) {
        return ThreadBasedUserCrnProvider.doAsInternalActor(
                regionAwareInternalCrnGeneratorFactory.iam().getInternalCrnForServiceAsString(),
                () -> stackV4Endpoint.getStatusByName(0L, sdxCluster.getClusterName(), sdxCluster.getAccountId()));
    }

    private boolean oneOf(Status status, Set<Status> statuses) {
        return status != null && statuses.contains(status);
    }

    private AttemptResult<StackStatusV4Response> failedPolling(String processDescription, SdxCluster sdxCluster, String statusReason) {
        LOGGER.info("{} failed: {}, ", processDescription, statusReason);
        return AttemptResults.breakFor(processDescription + " failed on '" + sdxCluster.getClusterName() + "' cluster. Reason: " + statusReason);
    }

    private void waitForFlowStateByFlowId(
            String process,
            String flowId,
            Long sdxId,
            PollingConfig pollingConfig,
            Set<FlowState> targetStates,
            Set<FlowState> failedStates) {
        Polling.waitPeriodly(pollingConfig.getSleepTime(), pollingConfig.getSleepTimeUnit())
                .stopIfException(pollingConfig.getStopPollingIfExceptionOccurred())
                .stopAfterDelay(pollingConfig.getDuration(), pollingConfig.getDurationTimeUnit())
                .run(() -> checkFlowStateByFlowId(process, flowId, sdxId, targetStates, failedStates));
    }

    private AttemptResult<FlowState> checkFlowStateByFlowId(
            String process,
            String flowId,
            Long sdxId,
            Set<FlowState> targetStates,
            Set<FlowState> failedStates) {
        LOGGER.info("Polling CB for flow state of process '{}' with flow ID '{}'.", process, flowId);
        if (PollGroup.CANCELLED.equals(DatalakeInMemoryStateStore.get(sdxId))) {
            LOGGER.info("{} polling cancelled in inmemory store, id: {}", process, sdxId);
            return AttemptResults.breakFor(process + " polling cancelled for flowId '" + flowId + "'.");
        }
        FlowState flowState = cloudbreakFlowService.getLastKnownFlowStateByFlowId(flowId);
        if (failedStates.contains(flowState)) {
            return AttemptResults.breakFor(process + " had flow with ID '" + flowId + "' fail with flow state: " + flowState.toString());
        } else if (targetStates.contains(flowState)) {
            return AttemptResults.finishWith(flowState);
        } else {
            return AttemptResults.justContinue();
        }
    }
}
