package com.sequenceiq.cloudbreak.reactor.handler.cluster.upgrade.rds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.common.database.TargetMajorVersion;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.cloudbreak.core.flow2.cluster.rds.upgrade.RdsSettingsMigrationService;
import com.sequenceiq.cloudbreak.dto.StackDto;
import com.sequenceiq.cloudbreak.eventbus.Event;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.upgrade.rds.UpgradeRdsFailedEvent;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.upgrade.rds.UpgradeRdsMigrateDatabaseSettingsRequest;
import com.sequenceiq.cloudbreak.reactor.api.event.cluster.upgrade.rds.UpgradeRdsMigrateDatabaseSettingsResponse;
import com.sequenceiq.cloudbreak.service.stack.StackDtoService;
import com.sequenceiq.cloudbreak.view.ClusterView;
import com.sequenceiq.flow.reactor.api.handler.HandlerEvent;

@ExtendWith(MockitoExtension.class)
public class MigrateDatabaseSettingsHandlerTest {

    @InjectMocks
    private MigrateDatabaseSettingsHandler underTest;

    @Mock
    private StackDtoService stackDtoService;

    @Mock
    private RdsSettingsMigrationService rdsSettingsMigrationService;

    @Mock
    private StackDto stackDto;

    @Mock
    private ClusterView clusterView;

    @Test
    void selector() {
        assertThat(underTest.selector()).isEqualTo("UPGRADERDSMIGRATEDATABASESETTINGSREQUEST");
    }

    @Test
    void testDoAccept() throws Exception {
        UpgradeRdsMigrateDatabaseSettingsRequest request = new UpgradeRdsMigrateDatabaseSettingsRequest(1L, TargetMajorVersion.VERSION_14);

        when(stackDto.getCluster()).thenReturn(clusterView);
        when(clusterView.getId()).thenReturn(345L);

        when(stackDtoService.getById(anyLong())).thenReturn(stackDto);
        Selectable result = underTest.doAccept(new HandlerEvent<>(new Event<>(request)));

        UpgradeRdsMigrateDatabaseSettingsResponse response = (UpgradeRdsMigrateDatabaseSettingsResponse) result;
        assertEquals(1L, response.getResourceId());

        verify(stackDtoService, times(1)).getById(anyLong());
        verify(rdsSettingsMigrationService).updateRdsConfigs(stackDto, Set.of());
        verify(rdsSettingsMigrationService).updateSaltPillars(stackDto, 345L);
        verify(rdsSettingsMigrationService).updateCMDatabaseConfiguration(stackDto);
    }

    @Test
    void testDoAcceptFailure() {
        UpgradeRdsMigrateDatabaseSettingsRequest request = new UpgradeRdsMigrateDatabaseSettingsRequest(1L, TargetMajorVersion.VERSION_14);
        Exception expectedException = new RuntimeException();
        when(stackDtoService.getById(anyLong())).thenThrow(expectedException);

        Selectable result = underTest.doAccept(new HandlerEvent<>(new Event<>(request)));
        UpgradeRdsFailedEvent response = (UpgradeRdsFailedEvent) result;
        assertEquals(expectedException, response.getException());
    }
}