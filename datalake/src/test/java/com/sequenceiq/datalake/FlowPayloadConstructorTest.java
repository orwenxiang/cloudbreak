package com.sequenceiq.datalake;

import org.junit.jupiter.api.Test;

import com.sequenceiq.flow.helper.FlowPayloadConstructorChecker;

class FlowPayloadConstructorTest {

    @Test
    void constructorTest() {
        new FlowPayloadConstructorChecker().checkConstructorOnAcceptableClasses();
    }

}
