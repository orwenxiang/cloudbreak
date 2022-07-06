package com.sequenceiq.cloudbreak.reactor.api.event.stack.loadbalancer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.reactor.api.event.StackEvent;

public class CreateLoadBalancerEntityRequest extends StackEvent {
    private final Stack stack;

    @JsonCreator
    public CreateLoadBalancerEntityRequest(
            @JsonProperty("stack") Stack stack) {
        super(stack.getId());
        this.stack = stack;
    }

    public Stack getStack() {
        return stack;
    }
}
