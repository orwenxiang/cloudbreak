package com.sequenceiq.it.cloudbreak.cloud;

public enum InstanceCountParameter {
    MASTER_INSTANCE_COUNT("masterInstanceCount"),
    WORKER_INSTANCE_COUNT("workerInstanceCount"),
    IDBROKER_INSTANCE_COUNT("idbrokerInstanceCount"),
    GATEWAY_INSTANCE_COUNT("gatewayInstanceCount"),
    COMPUTE_INSTANCE_COUNT("computeInstanceCount"),
    SERVICE_INSTANCE_COUNT("serviceInstanceCount"),
    NIFI_INSTANCE_COUNT("nifiInstanceCount"),
    ZOOKEEPER_INSTANCE_COUNT("zookeeperInstanceCount"),
    AUXILIARY_INSTANCE_COUNT("auxiliaryInstanceCount"),
    CORE_INSTANCE_COUNT("coreInstanceCount"),
    SOLR_SCALE_OUT_INSTANCE_COUNT("solrhgInstanceCount"),
    STORAGE_SCALE_OUT_INSTANCE_COUNT("storagehgInstanceCount"),
    KAFKA_SCALE_OUT_INSTANCE_COUNT("kafkahgInstanceCount"),
    RAZ_SCALE_OUT_INSTANCE_COUNT("razhgInstanceCount"),
    ATLAS_SCALE_OUT_INSTANCE_COUNT("atlashgInstanceCount"),
    HMS_SCALE_OUT_INSTANCE_COUNT("hmshgInstanceCount");

    private final String name;

    InstanceCountParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
