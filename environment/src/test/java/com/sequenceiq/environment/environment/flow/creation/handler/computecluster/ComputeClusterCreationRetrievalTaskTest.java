package com.sequenceiq.environment.environment.flow.creation.handler.computecluster;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sequenceiq.cloudbreak.cloud.scheduler.PollGroup;
import com.sequenceiq.environment.environment.service.externalizedcompute.ExternalizedComputeService;
import com.sequenceiq.environment.exception.ExternalizedComputeOperationFailedException;
import com.sequenceiq.environment.store.EnvironmentInMemoryStateStore;
import com.sequenceiq.externalizedcompute.api.model.ExternalizedComputeClusterApiStatus;
import com.sequenceiq.externalizedcompute.api.model.ExternalizedComputeClusterResponse;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
class ComputeClusterCreationRetrievalTaskTest {

    private static final long ENV_ID = 243_937_713L;

    private static final String ENV_CRN = "envCrn";

    private static final String COMPUTE_CLUSTER_NAME = "computeClusterName";

    private final ExternalizedComputeService externalizedComputeService = Mockito.mock(ExternalizedComputeService.class);

    private final ComputeClusterCreationRetrievalTask underTest = new ComputeClusterCreationRetrievalTask(externalizedComputeService);

    @AfterEach
    void cleanup() {
        EnvironmentInMemoryStateStore.delete(ENV_ID);
    }

    @Test
    void testExitPollingWhenComputeClusterIsInCreateInProgressState() {
        EnvironmentInMemoryStateStore.put(ENV_ID, PollGroup.CANCELLED);
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);

        boolean result = underTest.exitPolling(computeClusterPollerObject);

        assertTrue(result);
    }

    @Test
    void testExitPollingWhenComputeClusterIsInDeleteRelatedState() {
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);

        boolean result = underTest.exitPolling(computeClusterPollerObject);

        assertTrue(result);
    }

    @Test
    void testCheckStatusWithMissingComputeCluster() {
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);
        when(externalizedComputeService.getComputeClusterOptional(eq(ENV_CRN), eq(COMPUTE_CLUSTER_NAME))).thenReturn(Optional.empty());

        assertThrows(ExternalizedComputeOperationFailedException.class, () -> underTest.checkStatus(computeClusterPollerObject));
    }

    @Test
    void testCheckStatusWithDeleteInProgressState() {
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);
        ExternalizedComputeClusterResponse response = new ExternalizedComputeClusterResponse();
        response.setStatus(ExternalizedComputeClusterApiStatus.DELETE_IN_PROGRESS);
        response.setName(COMPUTE_CLUSTER_NAME);
        when(externalizedComputeService.getComputeClusterOptional(eq(ENV_CRN), eq(COMPUTE_CLUSTER_NAME))).thenReturn(Optional.of(response));

        assertThrows(ExternalizedComputeOperationFailedException.class, () -> underTest.checkStatus(computeClusterPollerObject));
    }

    @Test
    void testCheckStatusWithFailedState() {
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);
        ExternalizedComputeClusterResponse response = new ExternalizedComputeClusterResponse();
        response.setStatus(ExternalizedComputeClusterApiStatus.DELETE_IN_PROGRESS);
        response.setName(COMPUTE_CLUSTER_NAME);
        when(externalizedComputeService.getComputeClusterOptional(eq(ENV_CRN), eq(COMPUTE_CLUSTER_NAME))).thenReturn(Optional.of(response));

        assertThrows(ExternalizedComputeOperationFailedException.class, () -> underTest.checkStatus(computeClusterPollerObject));
    }

    @Test
    void testCheckStatusWithUnknownState() {
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);
        ExternalizedComputeClusterResponse response = new ExternalizedComputeClusterResponse();
        response.setStatus(ExternalizedComputeClusterApiStatus.UNKNOWN);
        response.setName(COMPUTE_CLUSTER_NAME);
        when(externalizedComputeService.getComputeClusterOptional(eq(ENV_CRN), eq(COMPUTE_CLUSTER_NAME))).thenReturn(Optional.of(response));

        assertThrows(ExternalizedComputeOperationFailedException.class, () -> underTest.checkStatus(computeClusterPollerObject));
    }

    @Test
    void testCheckStatusWithAvailableState() {
        ComputeClusterPollerObject computeClusterPollerObject = new ComputeClusterPollerObject(ENV_ID, ENV_CRN, COMPUTE_CLUSTER_NAME);
        ExternalizedComputeClusterResponse response = new ExternalizedComputeClusterResponse();
        response.setStatus(ExternalizedComputeClusterApiStatus.AVAILABLE);
        response.setName(COMPUTE_CLUSTER_NAME);
        when(externalizedComputeService.getComputeClusterOptional(eq(ENV_CRN), eq(COMPUTE_CLUSTER_NAME))).thenReturn(Optional.of(response));

        boolean result = underTest.checkStatus(computeClusterPollerObject);
        assertTrue(result);
        verify(externalizedComputeService, times(1)).getComputeClusterOptional(eq(ENV_CRN), eq(COMPUTE_CLUSTER_NAME));
    }

}