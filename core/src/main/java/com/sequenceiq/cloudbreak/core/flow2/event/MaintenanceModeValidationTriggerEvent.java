package com.sequenceiq.cloudbreak.core.flow2.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.common.event.AcceptResult;
import com.sequenceiq.cloudbreak.common.json.JsonIgnoreDeserialization;
import com.sequenceiq.cloudbreak.reactor.api.event.StackEvent;

import reactor.rx.Promise;

public class MaintenanceModeValidationTriggerEvent extends StackEvent {

    public MaintenanceModeValidationTriggerEvent(String selector, Long stackId) {
        super(selector, stackId);
    }

    @JsonCreator
    public MaintenanceModeValidationTriggerEvent(
            @JsonProperty("selector") String selector,
            @JsonProperty("resourceId") Long stackId,
            @JsonIgnoreDeserialization Promise<AcceptResult> accepted) {
        super(selector, stackId, accepted);
    }

}
