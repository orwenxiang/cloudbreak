package com.sequenceiq.cloudbreak.reactor.api.event.cluster.rotaterdscert;

import com.sequenceiq.cloudbreak.common.event.Payload;

public interface RotateRdsCertificateBaseEvent extends Payload {

    Long getResourceId();
}
