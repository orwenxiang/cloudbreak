package com.sequenceiq.cloudbreak.reactor.api.event.cluster.upgrade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.reactor.api.event.StackEvent;

public class ClusterUpgradeRequest extends StackEvent {

    private final boolean patchUpgrade;

    @JsonCreator
    public ClusterUpgradeRequest(
            @JsonProperty("resourceId") Long stackId,
            @JsonProperty("patchUpgrade") boolean patchUpgrade) {
        super(stackId);
        this.patchUpgrade = patchUpgrade;
    }

    public boolean isPatchUpgrade() {
        return patchUpgrade;
    }
}
