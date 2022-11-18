package com.sequenceiq.consumption.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.structuredevent.service.db.CDPStructuredEventDBService;
import com.sequenceiq.cloudbreak.util.TimeUtil;

@ExtendWith(MockitoExtension.class)
class ConsumptionStructuredEventCleanupServiceTest {

    @Mock
    private TimeUtil mockTimeUtil;

    @Mock
    private CDPStructuredEventDBService mockCDPStructuredEventService;

    @InjectMocks
    private ConsumptionStructuredEventCleanupService underTest;

    @Test
    @DisplayName("Test cleanUpStructuredEvents method with null passed. This way the expected resoult should be that the cleanup is not requested")
    void testCleanUpStructuredEventsWithNoResourceCrnProvided() {
        underTest.cleanUpStructuredEvents(null);

        verifyNoInteractions(mockCDPStructuredEventService);
    }

    @Test
    @DisplayName("Test cleanUpStructuredEvents method with empty String passed. This way the expected resoult should be that the cleanup is not requested")
    void testCleanUpStructuredEventsWithNullResourceCrnProvided() {
        underTest.cleanUpStructuredEvents("");

        verifyNoInteractions(mockCDPStructuredEventService);
    }

    @Test
    @DisplayName("Test cleanUpStructuredEvents method with proper content. This way the expected resoult should be that the cleanup is requested")
    void testCleanUpStructuredEventsWithContent() {
        String resourceCrn = "resourceCrn";
        when(mockTimeUtil.getTimestampThatMonthsBeforeNow(3)).thenReturn(1L);

        underTest.cleanUpStructuredEvents(resourceCrn);

        verify(mockCDPStructuredEventService, times(1)).deleteStructuredEventByResourceCrnThatIsOlderThan(resourceCrn, 1L);
        verifyNoMoreInteractions(mockCDPStructuredEventService);
    }

}