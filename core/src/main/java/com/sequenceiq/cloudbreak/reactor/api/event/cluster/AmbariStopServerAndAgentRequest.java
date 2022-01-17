package com.sequenceiq.cloudbreak.reactor.api.event.cluster;

import java.util.Set;

import com.sequenceiq.cloudbreak.reactor.api.event.resource.AbstractClusterScaleRequest;

public class AmbariStopServerAndAgentRequest extends AbstractClusterScaleRequest {
    public AmbariStopServerAndAgentRequest(Long stackId, Set<String> hostGroups) {
        super(stackId, hostGroups);
    }
}