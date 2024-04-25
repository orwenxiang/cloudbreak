package com.sequenceiq.cloudbreak.reactor.handler.cluster.upgrade.rds.rotaterdscert;

import static com.sequenceiq.cloudbreak.core.flow2.cluster.rds.rotaterdscert.RotateRdsCertificateEvent.ROLLING_RESTART_SERVICES_FINISHED_EVENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.core.flow2.cluster.rds.rotaterdscert.RotateRdsCertificateService;
import com.sequenceiq.cloudbreak.eventbus.Event;
import com.sequenceiq.cloudbreak.eventbus.EventBus;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.rotaterdscert.RollingRestartServicesRequest;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.rotaterdscert.RollingRestartServicesResult;

@ExtendWith(MockitoExtension.class)
class RollingRestartServicesHandlerTest {
    private static final long STACK_ID = 234L;

    @Mock
    private RotateRdsCertificateService rotateRdsCertificateService;

    @Mock
    private EventBus eventBus;

    @Captor
    private ArgumentCaptor<Event<RollingRestartServicesResult>> eventCaptor;

    @InjectMocks
    private RollingRestartServicesHandler underTest;

    private Event<RollingRestartServicesRequest> event;

    @BeforeEach
    void setUp() {
        RollingRestartServicesRequest request = new RollingRestartServicesRequest(STACK_ID);
        event = new Event<>(request);
    }

    @Test
    void rollingRestartServices() {
        underTest.accept(event);
        verify(rotateRdsCertificateService).rollingRestartServices(STACK_ID);
        verify(eventBus).notify(eq(ROLLING_RESTART_SERVICES_FINISHED_EVENT.event()), eventCaptor.capture());
        Event<RollingRestartServicesResult> eventResult = eventCaptor.getValue();
        assertThat(eventResult.getData().selector()).isEqualTo(ROLLING_RESTART_SERVICES_FINISHED_EVENT.event());
        assertThat(eventResult.getData().getResourceId()).isEqualTo(STACK_ID);
    }

}
