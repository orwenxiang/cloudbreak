package com.sequenceiq.cloudbreak.core.flow2.cluster.verticalscale.diskupdate.event;

import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.reactor.api.event.StackEvent;

public class DistroXDiskResizeHandlerRequest extends StackEvent {

    private final String instanceGroup;

    @JsonCreator
    public DistroXDiskResizeHandlerRequest(
            @JsonProperty("selector") String selector,
            @JsonProperty("resourceId") Long stackId,
            @JsonProperty("instanceGroup") String instanceGroup) {
        super(selector, stackId);
        this.instanceGroup = instanceGroup;
    }

    public String getInstanceGroup() {
        return instanceGroup;
    }

    public String toString() {
        return new StringJoiner(", ", DistroXDiskResizeHandlerRequest.class.getSimpleName() + "[", "]")
                .add("instanceGroup=" + instanceGroup)
                .toString();
    }
}
