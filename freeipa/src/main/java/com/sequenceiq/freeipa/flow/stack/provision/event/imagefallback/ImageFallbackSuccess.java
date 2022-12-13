package com.sequenceiq.freeipa.flow.stack.provision.event.imagefallback;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.freeipa.flow.stack.StackEvent;

public class ImageFallbackSuccess extends StackEvent {

    @JsonCreator
    public ImageFallbackSuccess(
            @JsonProperty("resourceId") Long stackId) {
        super(stackId);
    }

}
