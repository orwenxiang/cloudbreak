package com.sequenceiq.freeipa.flow.freeipa.provision.event.orchestrator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.freeipa.flow.stack.StackEvent;

public class OrchestratorConfigSuccess extends StackEvent {
    @JsonCreator
    public OrchestratorConfigSuccess(
            @JsonProperty("resourceId") Long stackId) {
        super(stackId);
    }
}
