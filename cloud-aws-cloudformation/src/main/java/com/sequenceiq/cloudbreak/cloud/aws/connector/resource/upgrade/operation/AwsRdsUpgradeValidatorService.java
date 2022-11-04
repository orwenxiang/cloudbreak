package com.sequenceiq.cloudbreak.cloud.aws.connector.resource.upgrade.operation;

import static com.sequenceiq.cloudbreak.cloud.aws.connector.resource.upgrade.operation.AwsRdsState.AVAILABLE;
import static com.sequenceiq.cloudbreak.cloud.aws.connector.resource.upgrade.operation.AwsRdsState.UPGRADING;
import static com.sequenceiq.cloudbreak.cloud.aws.connector.resource.upgrade.operation.RdsState.OTHER;
import static com.sequenceiq.cloudbreak.cloud.aws.connector.resource.upgrade.operation.RdsState.UNKNOWN;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DBParameterGroupStatus;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.sequenceiq.cloudbreak.cloud.aws.connector.resource.AwsRdsStatusLookupService;
import com.sequenceiq.cloudbreak.cloud.context.AuthenticatedContext;
import com.sequenceiq.cloudbreak.cloud.exception.CloudConnectorException;
import com.sequenceiq.cloudbreak.cloud.model.DatabaseStack;
import com.sequenceiq.cloudbreak.cloud.notification.PersistenceNotifier;
import com.sequenceiq.cloudbreak.common.database.TargetMajorVersion;
import com.sequenceiq.cloudbreak.common.database.Version;
import com.sequenceiq.cloudbreak.util.MajorVersionComparator;

@Service
public class AwsRdsUpgradeValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsRdsUpgradeValidatorService.class);

    @Inject
    private AwsRdsStatusLookupService awsRdsStatusLookupService;

    public void validateUpgradePresentForTargetMajorVersion(Optional<RdsEngineVersion> upgradeTargetForMajorVersion) {
        if (upgradeTargetForMajorVersion.isEmpty()) {
            String message = "There are no matching RDS upgrade versions to choose from.";
            LOGGER.warn(message);
            throw new CloudConnectorException(message);
        }
    }

    public void validateClusterHasASingleVersion(Set<String> dbVersions) {
        if (dbVersions.isEmpty()) {
            String message = "Current DB version could not be obtained";
            LOGGER.warn(message);
            throw new CloudConnectorException(message);
        }

        if (dbVersions.size() > 1) {
            String message = String.format("RDS is on multiple versions (%s), cannot proceed with its upgrade", dbVersions);
            LOGGER.warn(message);
            throw new CloudConnectorException(message);
        }
    }

    public void validateRdsIsAvailableOrUpgrading(RdsInfo rdsInfo) {
        if (OTHER == rdsInfo.getRdsState() || UNKNOWN == rdsInfo.getRdsState()) {
            String message = String.format("RDS upgrade is not possible as one or more instances are not in a correct state: %s",
                    getNotApplicableStates(rdsInfo.getDbArnToInstanceStatuses()));
            LOGGER.warn(message);
            throw new CloudConnectorException(message);
        }
    }

    public boolean isRdsMajorVersionSmallerThanTarget(RdsInfo rdsInfo, Version targetMajorVersion) {
        MajorVersionComparator majorVersionComparator = new MajorVersionComparator();
        RdsEngineVersion currentVersion = rdsInfo.getRdsEngineVersion();
        RdsEngineVersion targetVersion = new RdsEngineVersion(targetMajorVersion.getMajorVersion());
        boolean upgradeNeeded = majorVersionComparator.compare(currentVersion, targetVersion) < 0;
        LOGGER.debug("Comparing current DB version to target major version if an upgrade is needed. Current version: {}. target version: {}. upgarde needed: {}",
                currentVersion, targetVersion, upgradeNeeded);
        return upgradeNeeded;
    }

    public void validateCustomPropertiesAdded(AuthenticatedContext authenticatedContext, DatabaseStack stack, PersistenceNotifier persistenceNotifier,
        TargetMajorVersion targetMajorVersion) {
        if (stack.getDatabaseServer().isUseSslEnforcement()) {
            return;
        }
        DescribeDBInstancesResult describeDBInstancesResult = awsRdsStatusLookupService.getDescribeDBInstancesResult(authenticatedContext, stack);
        if (describeDBInstancesResult != null && CollectionUtils.isNotEmpty(describeDBInstancesResult.getDBInstances())) {
            DBInstance dbInstance = describeDBInstancesResult.getDBInstances().get(0);
            if (CollectionUtils.isNotEmpty(dbInstance.getDBParameterGroups())) {
                List<String> nonDefaultParamGroupNames = dbInstance.getDBParameterGroups().stream()
                        .map(DBParameterGroupStatus::getDBParameterGroupName)
                        .filter(groupName -> !groupName.startsWith("default."))
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(nonDefaultParamGroupNames)) {
                    String message = String.format(
                            "The following custom parameter groups are attached to the RDS instance [%s]: %s. Please remove them before RDS upgrade.",
                            stack.getDatabaseServer().getServerId(), nonDefaultParamGroupNames.stream().collect(Collectors.joining(", ")));
                    LOGGER.warn(message);
                    throw new CloudConnectorException(message);
                }
            }
        }
    }

    private Set<String> getNotApplicableStates(Map<String, String> dbArnToInstanceStatuses) {
        return dbArnToInstanceStatuses.entrySet().stream()
                .filter(entry -> !AVAILABLE.getState().equals(entry.getValue())
                        && !UPGRADING.getState().equals(entry.getValue()))
                .map(entry -> String.format("arn: %s => status: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

}