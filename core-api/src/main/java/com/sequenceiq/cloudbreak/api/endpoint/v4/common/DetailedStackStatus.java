package com.sequenceiq.cloudbreak.api.endpoint.v4.common;

import java.util.List;

public enum DetailedStackStatus {
    UNKNOWN(null),
    // Provision statuses
    PROVISION_REQUESTED(Status.REQUESTED),
    PROVISION_SETUP(Status.CREATE_IN_PROGRESS),
    IMAGE_SETUP(Status.CREATE_IN_PROGRESS),
    CREATING_INFRASTRUCTURE(Status.CREATE_IN_PROGRESS),
    CREATING_LOAD_BALANCER(Status.CREATE_IN_PROGRESS),
    METADATA_COLLECTION(Status.CREATE_IN_PROGRESS),
    LOAD_BALANCER_METADATA_COLLECTION(Status.CREATE_IN_PROGRESS),
    TLS_SETUP(Status.CREATE_IN_PROGRESS),
    PROVISIONED(Status.CREATE_IN_PROGRESS),
    PROVISION_FAILED(Status.CREATE_FAILED),
    CLUSTER_RESET_FAILED(Status.CREATE_FAILED),
    CLUSTER_CREATE_FAILED(Status.CREATE_FAILED),
    // Orchestration statuses
    REGISTERING_TO_CLUSTER_PROXY(Status.UPDATE_IN_PROGRESS),
    REGISTERING_GATEWAY_TO_CLUSTER_PROXY(Status.UPDATE_IN_PROGRESS),
    BOOTSTRAPPING_MACHINES(Status.UPDATE_IN_PROGRESS),
    COLLECTING_HOST_METADATA(Status.UPDATE_IN_PROGRESS),
    VALIDATING_CLOUD_STORAGE_ON_VM(Status.UPDATE_IN_PROGRESS),
    MOUNTING_DISKS(Status.UPDATE_IN_PROGRESS),
    STARTING_CLUSTER_MANAGER_SERVICES(Status.UPDATE_IN_PROGRESS),
    // Start statuses
    @Deprecated
    START_REQUESTED(Status.START_REQUESTED),
    START_IN_PROGRESS(Status.START_IN_PROGRESS),
    STACK_STARTED(Status.START_IN_PROGRESS),
    START_FAILED(Status.START_FAILED),
    // Stop statuses
    @Deprecated
    STOP_REQUESTED(Status.STOP_REQUESTED),
    STOP_IN_PROGRESS(Status.STOP_IN_PROGRESS),
    CLUSTER_STOPPED(Status.STOP_IN_PROGRESS),
    STACK_STOPPED(Status.STOP_IN_PROGRESS),
    STOPPED(Status.STOPPED),
    STOP_FAILED(Status.STOP_FAILED),
    // Upscale statuses
    UPSCALE_REQUESTED(Status.UPDATE_REQUESTED),
    UPSCALE_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    ADDING_NEW_INSTANCES(Status.UPDATE_IN_PROGRESS),
    EXTENDING_METADATA(Status.UPDATE_IN_PROGRESS),
    EXTENDING_METADATA_FINISHED(Status.UPDATE_IN_PROGRESS),
    BOOTSTRAPPING_NEW_NODES(Status.UPDATE_IN_PROGRESS),
    EXTENDING_HOST_METADATA(Status.UPDATE_IN_PROGRESS),
    MOUNTING_DISKS_ON_NEW_HOSTS(Status.UPDATE_IN_PROGRESS),
    STACK_UPSCALE_COMPLETED(Status.UPDATE_IN_PROGRESS),
    UPSCALE_FAILED(Status.UPDATE_FAILED),
    // Downscale statuses
    DOWNSCALE_REQUESTED(Status.UPDATE_REQUESTED),
    DOWNSCALE_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    DOWNSCALE_COMPLETED(Status.AVAILABLE),
    DOWNSCALE_FAILED(Status.AVAILABLE),
    // Upscale by starting statuses
    UPSCALE_BY_START_REQUESTED(Status.UPDATE_REQUESTED),
    UPSCALE_BY_START_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    UPSCALE_BY_START_FAILED(Status.AVAILABLE),
    // Downscale by stopping statuses
    DOWNSCALE_BY_STOP_REQUESTED(Status.UPDATE_REQUESTED),
    DOWNSCALE_BY_STOP_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    DOWNSCALE_BY_STOP_FAILED(Status.AVAILABLE),
    // Termination statuses
    PRE_DELETE_IN_PROGRESS(Status.PRE_DELETE_IN_PROGRESS),
    DELETE_IN_PROGRESS(Status.DELETE_IN_PROGRESS),
    DEREGISTERING_CCM_KEY(Status.DELETE_IN_PROGRESS),
    DELETE_COMPLETED(Status.DELETE_COMPLETED),
    DELETE_FAILED(Status.DELETE_FAILED),
    DELETED_ON_PROVIDER_SIDE(Status.DELETED_ON_PROVIDER_SIDE),
    // Rollback statuses
    ROLLING_BACK(Status.UPDATE_IN_PROGRESS),
    // The stack is available
    AVAILABLE(Status.AVAILABLE),
    AVAILABLE_WITH_STOPPED_INSTANCES(Status.AVAILABLE),
    // Instance removing status
    REMOVE_INSTANCE(Status.UPDATE_IN_PROGRESS),
    // Cluster operation is in progress
    CLUSTER_OPERATION(Status.UPDATE_IN_PROGRESS),
    // Wait for sync
    WAIT_FOR_SYNC(Status.WAIT_FOR_SYNC),
    // Retry
    RETRY(Status.UPDATE_IN_PROGRESS),
    // Repair status
    REPAIR_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    REPAIR_FAILED(Status.UPDATE_FAILED),
    // External database statuses
    EXTERNAL_DATABASE_CREATION_IN_PROGRESS(Status.EXTERNAL_DATABASE_CREATION_IN_PROGRESS),
    EXTERNAL_DATABASE_CREATION_FAILED(Status.EXTERNAL_DATABASE_CREATION_FAILED),
    EXTERNAL_DATABASE_DELETION_IN_PROGRESS(Status.EXTERNAL_DATABASE_DELETION_IN_PROGRESS),
    EXTERNAL_DATABASE_DELETION_FINISHED(Status.EXTERNAL_DATABASE_DELETION_FINISHED),
    EXTERNAL_DATABASE_DELETION_FAILED(Status.EXTERNAL_DATABASE_DELETION_FAILED),
    EXTERNAL_DATABASE_START_IN_PROGRESS(Status.EXTERNAL_DATABASE_START_IN_PROGRESS),
    EXTERNAL_DATABASE_START_FINISHED(Status.EXTERNAL_DATABASE_START_FINISHED),
    EXTERNAL_DATABASE_START_FAILED(Status.EXTERNAL_DATABASE_START_FAILED),
    EXTERNAL_DATABASE_STOP_IN_PROGRESS(Status.EXTERNAL_DATABASE_STOP_IN_PROGRESS),
    @Deprecated
    EXTERNAL_DATABASE_STOP_FINISHED(Status.EXTERNAL_DATABASE_STOP_FINISHED),
    EXTERNAL_DATABASE_STOP_FAILED(Status.EXTERNAL_DATABASE_STOP_FAILED),

    DATABASE_UPGRADE_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    DATABASE_UPGRADE_FINISHED(Status.AVAILABLE),
    DATABASE_UPGRADE_FAILED(Status.EXTERNAL_DATABASE_UPGRADE_FAILED),
    EXTERNAL_DATABASE_UPGRADE_VALIDATION_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    EXTERNAL_DATABASE_UPGRADE_VALIDATION_FINISHED(Status.AVAILABLE),
    EXTERNAL_DATABASE_UPGRADE_VALIDATION_FAILED(Status.AVAILABLE),
    // Embedded database statuses
    PREPARE_EMBEDDED_DATABASE_UPGRADE_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    PREPARE_EMBEDDED_DATABASE_UPGRADE_FINISHED(Status.AVAILABLE),
    PREPARE_EMBEDDED_UPGRADE_FAILED(Status.UPDATE_FAILED),
    // Upgrade statuses
    CLUSTER_MANAGER_NOT_RESPONDING(Status.UNREACHABLE),
    NODE_FAILURE(Status.NODE_FAILURE),
    CLUSTER_UPGRADE_INIT_FAILED(Status.AVAILABLE),
    CLUSTER_MANAGER_UPGRADE_FAILED(Status.UPDATE_FAILED),
    CLUSTER_UPGRADE_STARTED(Status.UPDATE_IN_PROGRESS),
    CLUSTER_UPGRADE_PREPARATION_STARTED(Status.UPDATE_IN_PROGRESS),
    CLUSTER_UPGRADE_PREPARATION_FINISHED(Status.AVAILABLE),
    CLUSTER_UPGRADE_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    CLUSTER_UPGRADE_FAILED(Status.UPDATE_FAILED),
    CLUSTER_UPGRADE_FINISHED(Status.AVAILABLE),
    CLUSTER_UPGRADE_VALIDATION_STARTED(Status.UPDATE_IN_PROGRESS),
    CLUSTER_UPGRADE_VALIDATION_FINISHED(Status.AVAILABLE),
    CLUSTER_UPGRADE_VALIDATION_SKIPPED(Status.AVAILABLE),
    CLUSTER_UPGRADE_VALIDATION_FAILED(Status.AVAILABLE),
    STACK_IMAGE_UPDATE_FAILED(Status.UPDATE_FAILED),
    CLUSTER_RECOVERY_IN_PROGRESS(Status.RECOVERY_IN_PROGRESS),
    CLUSTER_RECOVERY_FAILED(Status.RECOVERY_FAILED),
    CLUSTER_RECOVERY_FINISHED(Status.AVAILABLE),
    // Database backup/restore statuses
    DATABASE_BACKUP_IN_PROGRESS(Status.BACKUP_IN_PROGRESS),
    DATABASE_BACKUP_FINISHED(Status.AVAILABLE),
    DATABASE_BACKUP_FAILED(Status.BACKUP_FAILED),
    DATABASE_RESTORE_IN_PROGRESS(Status.RESTORE_IN_PROGRESS),
    DATABASE_RESTORE_FINISHED(Status.AVAILABLE),
    DATABASE_RESTORE_FAILED(Status.RESTORE_FAILED),
    // Load balancer update status
    CREATE_LOAD_BALANCER_ENTITY(Status.UPDATE_IN_PROGRESS),
    CREATE_CLOUD_LOAD_BALANCER(Status.UPDATE_IN_PROGRESS),
    COLLECT_LOAD_BALANCER_METADATA(Status.UPDATE_IN_PROGRESS),
    LOAD_BALANCER_REGISTER_PUBLIC_DNS(Status.UPDATE_IN_PROGRESS),
    LOAD_BALANCER_REGISTER_FREEIPA_DNS(Status.UPDATE_IN_PROGRESS),
    LOAD_BALANCER_UPDATE_CM_CONFIG(Status.UPDATE_IN_PROGRESS),
    LOAD_BALANCER_RESTART_CM(Status.UPDATE_IN_PROGRESS),
    LOAD_BALANCER_UPDATE_FINISHED(Status.AVAILABLE),
    LOAD_BALANCER_UPDATE_FAILED(Status.UPDATE_FAILED),
    SALT_UPDATE_FAILED(Status.UPDATE_FAILED),
    PILLAR_CONFIG_UPDATE_FAILED(Status.UPDATE_FAILED),
    CERTIFICATES_ROTATION_FAILED(Status.UPDATE_FAILED),
    CERTIFICATES_ROTATION_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    CERTIFICATE_RENEWAL_FAILED(Status.UPDATE_FAILED),
    CERTIFICATE_RENEWAL_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    CERTIFICATE_REDEPLOY_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    PRIMARY_GATEWAY_CHANGE_FAILED(Status.UPDATE_FAILED),
    MAINTENANCE_MODE_ENABLED(Status.MAINTENANCE_MODE_ENABLED),
    CLUSTER_DELETE_IN_PROGRESS(Status.DELETE_IN_PROGRESS),
    CLUSTER_DELETE_COMPLETED(Status.DELETE_IN_PROGRESS),
    CLUSTER_DELETE_FAILED(Status.DELETE_FAILED),
    CLUSTER_RECREATE_REQUESTED(Status.REQUESTED),
    CCM_UPGRADE_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    CCM_UPGRADE_FAILED(Status.UPDATE_FAILED),
    CLUSTER_RESTART_IN_PROGRESS(Status.UPDATE_IN_PROGRESS),
    CLUSTER_RESTART_FAILED(Status.UPDATE_FAILED);

    private final Status status;

    DetailedStackStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public static List<DetailedStackStatus> getUpgradeFailureStatuses() {
        return List.of(
                DetailedStackStatus.CLUSTER_UPGRADE_FAILED,
                DetailedStackStatus.CLUSTER_MANAGER_UPGRADE_FAILED,
                DetailedStackStatus.CLUSTER_UPGRADE_INIT_FAILED);
    }
}
