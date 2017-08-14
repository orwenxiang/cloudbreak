package com.sequenceiq.cloudbreak.orchestrator.container;

public enum DockerContainer {

    AMBARI_SERVER("ambari-server"),
    AMBARI_AGENT("ambari-agent"),
    AMBARI_DB("ambari_db"),
    KERBEROS("kerberos"),
    REGISTRATOR("registrator"),
    MUNCHAUSEN("munchausen"),
    CONSUL_WATCH("consul-watch"),
    LOGROTATE("logrotate"),
    HAVEGED("haveged"),
    SHIPYARD("shipyard"),
    SHIPYARD_DB("shipyard_db");

    private final String name;

    DockerContainer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
