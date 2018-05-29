package com.sequenceiq.cloudbreak.core.flow2.stack;

public enum Msg {
    STACK_INFRASTRUCTURE_BOOTSTRAP("stack.infrastructure.bootstrap"),
    STACK_INFRASTRUCTURE_METADATA_SETUP("stack.infrastructure.metadata.setup"),
    STACK_INFRASTRUCTURE_STARTING("stack.infrastructure.starting"),
    STACK_INFRASTRUCTURE_STARTED("stack.infrastructure.started"),
    STACK_BILLING_STARTED("stack.billing.started"),
    STACK_BILLING_STOPPED("stack.billing.stopped"),
    STACK_BILLING_TERMINATED("stack.billing.terminated"),
    STACK_INFRASTRUCTURE_STOPPING("stack.infrastructure.stopping"),
    STACK_INFRASTRUCTURE_STOPPED("stack.infrastructure.stopped"),
    STACK_NOTIFICATION_EMAIL("stack.notification.email"),
    STACK_DELETE_IN_PROGRESS("stack.delete.in.progress"),
    STACK_PRE_DELETE_IN_PROGRESS("stack.pre.delete.in.progress"),
    STACK_DELETE_COMPLETED("stack.delete.completed"),
    STACK_FORCED_DELETE_COMPLETED("stack.forced.delete.completed"),
    STACK_ADDING_INSTANCES("stack.adding.instances"),
    STACK_REMOVING_INSTANCE("stack.removing.instance"),
    STACK_REMOVING_INSTANCE_FINISHED("stack.removing.instance.finished"),
    STACK_REMOVING_INSTANCE_FAILED("stack.removing.instance.failed"),
    STACK_METADATA_EXTEND("stack.metadata.extend"),
    STACK_BOOTSTRAP_NEW_NODES("stack.bootstrap.new.nodes"),
    STACK_UPSCALE_FINISHED("stack.upscale.finished"),
    STACK_DOWNSCALE_INSTANCES("stack.downscale.instances"),
    STACK_DOWNSCALE_SUCCESS("stack.downscale.success"),
    STACK_DOWNSCALE_FAILED("stack.downscale.failed"),
    STACK_SELECT_FOR_DOWNSCALE("stack.select.for.downscale"),
    STACK_STOP_REQUESTED("stack.stop.requested"),
    STACK_PROVISIONING("stack.provisioning"),
    STACK_INFRASTRUCTURE_TIME("stack.infrastructure.time"),
    STACK_INFRASTRUCTURE_SUBNETS_UPDATING("stack.infrastructure.subnets.updating"),
    STACK_INFRASTRUCTURE_SUBNETS_UPDATED("stack.infrastructure.subnets.updated"),
    STACK_INFRASTRUCTURE_UPDATE_FAILED("stack.infrastructure.update.failed"),
    STACK_INFRASTRUCTURE_CREATE_FAILED("stack.infrastructure.create.failed"),
    STACK_INFRASTRUCTURE_ROLLBACK_FAILED("stack.infrastructure.rollback.failed"),
    STACK_INFRASTRUCTURE_DELETE_FAILED("stack.infrastructure.delete.failed"),
    STACK_INFRASTRUCTURE_START_FAILED("stack.infrastructure.start.failed"),
    STACK_INFRASTRUCTURE_STOP_FAILED("stack.infrastructure.stop.failed"),
    STACK_INFRASTRUCTURE_ROLLBACK_MESSAGE("stack.infrastructure.create.rollback"),
    STACK_SYNC_INSTANCE_STATUS_COULDNT_DETERMINE("stack.sync.instance.status.couldnt.determine"),
    STACK_REPAIR_DETECTION_STARTED("stack.repair.detection.started"),
    STACK_REPAIR_ATTEMPTING("stack.repair.attempting"),
    STACK_REPAIR_TRIGGERED("stack.repair.triggered"),
    STACK_REPAIR_COMPLETE_CLEAN("stack.repair.complete.clean"),
    STACK_REPAIR_FAILED("stack.repair.failed"),
    STACK_DATALAKE_UPDATE("stack.datalake.update"),
    STACK_DATALAKE_UPDATE_FINISHED("stack.datalake.update.finished"),
    STACK_DATALAKE_UPDATE_FAILED("stack.datalake.update.failed"),
    FLOW_STACK_PROVISIONED_BILLING("flow.stack.provisioned.billing"),
    FLOW_STACK_PROVISIONED("flow.stack.provisioned"),
    FLOW_STACK_METADATA_COLLECTED("stack.metadata.collected"),
    CLUSTER_DELETE_COMPLETED("ambari.cluster.delete.completed"),
    CLUSTER_EMAIL_SENT("ambari.cluster.notification.email"),
    CLUSTER_DELETE_FAILED("ambari.cluster.delete.failed"),
    STACK_SCALING_TERMINATING_HOST_FROM_HOSTGROUP("stack.scaling.terminating.host.from.hostgroup"),
    AMBARI_CLUSTER_RUN_CONTAINERS("ambari.cluster.run.containers"),
    AMBARI_CLUSTER_RUN_SERVICES("ambari.cluster.run.services"),
    AMBARI_CLUSTER_BUILDING("ambari.cluster.building"),
    AMBARI_CLUSTER_BUILT("ambari.cluster.built"),
    AMBARI_CLUSTER_CREATE_FAILED("ambari.cluster.create.failed"),
    AMBARI_CLUSTER_SCALING_UP("ambari.cluster.scaling.up"),
    AMBARI_CLUSTER_SCALED_UP("ambari.cluster.scaled.up"),
    AMBARI_CLUSTER_SCALING_DOWN("ambari.cluster.scaling.down"),
    AMBARI_CLUSTER_SCALED_DOWN("ambari.cluster.scaled.down"),
    AMBARI_CLUSTER_SCALING_FAILED("ambari.cluster.scaling.failed"),
    AMBARI_CLUSTER_STARTING("ambari.cluster.starting"),
    AMBARI_CLUSTER_STARTED("ambari.cluster.started"),
    AMBARI_CLUSTER_START_FAILED("ambari.cluster.start.failed"),
    AMBARI_CLUSTER_STOPPING("ambari.cluster.stopping"),
    AMBARI_CLUSTER_STOPPED("ambari.cluster.stopped"),
    AMBARI_CLUSTER_STOP_FAILED("ambari.cluster.stop.failed"),
    AMBARI_CLUSTER_NOTIFICATION_EMAIL("ambari.cluster.notification.email"),
    AMBARI_CLUSTER_RESET("ambari.cluster.reset"),
    AMBARI_CLUSTER_CHANGED_CREDENTIAL("ambari.cluster.changed.credential"),
    AMBARI_CLUSTER_CHANGING_CREDENTIAL("ambari.cluster.changing.credential"),
    AMBARI_CLUSTER_CHANGE_CREDENTIAL_FAILED("ambari.cluster.change.credentail.failed"),
    AMBARI_CLUSTER_UPGRADE("ambari.cluster.upgrade"),
    AMBARI_CLUSTER_UPGRADE_FAILED("ambari.cluster.upgrade.failed"),
    AMBARI_CLUSTER_UPGRADE_FINISHED("ambari.cluster.upgrade.finished"),
    AMBARI_CLUSTER_REMOVING_NODE_FROM_HOSTGROUP("ambari.cluster.removing.node.from.hostgroup"),
    AMBARI_CLUSTER_GATEWAY_CHANGE("ambari.cluster.gateway.change"),
    AMBARI_CLUSTER_GATEWAY_CHANGED_SUCCESSFULLY("ambari.cluster.gateway.changed.successfully"),
    AMBARI_CLUSTER_GATEWAY_CHANGE_FAILED("ambari.cluster.gateway.change.failed"),
    STACK_IMAGE_SETUP("stack.image.setup");

    private final String code;

    Msg(String msgCode) {
        code = msgCode;
    }

    public String code() {
        return code;
    }
}
