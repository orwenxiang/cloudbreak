package com.sequenceiq.freeipa.flow.stack.provision.event.clusterproxy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.freeipa.flow.stack.StackEvent;

public class ClusterProxyRegistrationRequest extends StackEvent {
    @JsonCreator
    public ClusterProxyRegistrationRequest(
            @JsonProperty("resourceId") Long stackId) {
        super(stackId);
    }
}
