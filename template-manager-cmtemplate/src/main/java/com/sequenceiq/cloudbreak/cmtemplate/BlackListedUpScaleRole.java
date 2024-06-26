package com.sequenceiq.cloudbreak.cmtemplate;

import java.util.Optional;

import com.sequenceiq.cloudbreak.auth.altus.model.Entitlement;
import com.sequenceiq.cloudbreak.common.type.Versioned;

public enum BlackListedUpScaleRole implements BlackListedScaleRole {
    KAFKA_BROKER("7.2.12"),
    NIFI_NODE("7.2.8"),
    ZEPPELIN_SERVER,
    NAMENODE;

    private final Optional<Entitlement> entitledFor;

    private final Optional<String> blockedUntilCDPVersion;

    private final Optional<String> requiredService;

    BlackListedUpScaleRole() {
        this(null, null);
    }

    BlackListedUpScaleRole(Entitlement entitledFor) {
        this(entitledFor, null);
    }

    BlackListedUpScaleRole(String blockedUntilCDPVersion) {
        this(null, blockedUntilCDPVersion, null);

    }

    BlackListedUpScaleRole(Entitlement entitledFor, String blockedUntilCDPVersion) {
        this(entitledFor, blockedUntilCDPVersion, null);
    }

    BlackListedUpScaleRole(Entitlement entitledFor, String blockedUntilCDPVersion, String requiredService) {
        this.entitledFor = Optional.ofNullable(entitledFor);
        this.blockedUntilCDPVersion = Optional.ofNullable(blockedUntilCDPVersion);
        this.requiredService = Optional.ofNullable(requiredService);
    }

    @Override
    public Optional<Entitlement> getEntitledFor() {
        return entitledFor;
    }

    @Override
    public Optional<String> getBlockedUntilCDPVersion() {
        return blockedUntilCDPVersion;
    }

    @Override
    public Optional<String> getRequiredService() {
        return requiredService;
    }

    public Versioned getBlockedUntilCDPVersionAsVersion() {
        return () -> blockedUntilCDPVersion.orElse(null);
    }

    @Override
    public String scaleType() {
        return "up";
    }
}
