package com.sequenceiq.freeipa.flow;

import org.junit.jupiter.api.Test;

import com.sequenceiq.flow.EnforceEntityDenialUtil;

public class EnforceEntityDenialInFlowPayloadTest {

    @Test
    public void enforceDenialOfEntitiesInFlowPayload() {
        EnforceEntityDenialUtil.denyEntity();
    }
}
