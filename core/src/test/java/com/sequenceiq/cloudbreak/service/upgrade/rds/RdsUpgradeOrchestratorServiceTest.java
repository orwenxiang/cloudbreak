package com.sequenceiq.cloudbreak.service.upgrade.rds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.common.orchestration.Node;
import com.sequenceiq.cloudbreak.core.bootstrap.service.ClusterDeletionBasedExitCriteriaModel;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.dto.StackDto;
import com.sequenceiq.cloudbreak.orchestrator.exception.CloudbreakOrchestratorException;
import com.sequenceiq.cloudbreak.orchestrator.host.HostOrchestrator;
import com.sequenceiq.cloudbreak.orchestrator.host.OrchestratorStateParams;
import com.sequenceiq.cloudbreak.orchestrator.model.GatewayConfig;
import com.sequenceiq.cloudbreak.service.GatewayConfigService;
import com.sequenceiq.cloudbreak.service.stack.StackDtoService;
import com.sequenceiq.cloudbreak.util.StackUtil;

@ExtendWith(MockitoExtension.class)
class RdsUpgradeOrchestratorServiceTest {

    private static final long STACK_ID = 123L;

    @Mock
    private StackDtoService stackDtoService;

    @Mock
    private UpgradeEmbeddedDBStateParamsProvider upgradeEmbeddedDBStateParamsProvider;

    @Mock
    private UpgradeEmbeddedDBPreparationStateParamsProvider upgradeEmbeddedDBPreparationStateParamsProvider;

    @Mock
    private BackupRestoreEmbeddedDBStateParamsProvider backupRestoreEmbeddedDBStateParamsProvider;

    @Mock
    private GatewayConfigService gatewayConfigService;

    @Mock
    private StackUtil stackUtil;

    @Mock
    private HostOrchestrator hostOrchestrator;

    @Mock
    private StackDto stack;

    @Mock
    private Cluster cluster;

    @Captor
    private ArgumentCaptor<OrchestratorStateParams> paramCaptor;

    @InjectMocks
    private RdsUpgradeOrchestratorService underTest;

    @Mock
    private GatewayConfig gatewayConfig;

    private Node node1;

    private Node node2;

    @BeforeEach
    void setUp() {
        node1 = new Node("privateIP1", "publicIP1", "instance1", "instanceType1", "fqdn1", "hostgroup");
        node2 = new Node("privateIP2", "publicIP2", "instance2", "instanceType2", "fqdn2", "hostgroup");
        when(stack.getId()).thenReturn(STACK_ID);
        when(stack.getCluster()).thenReturn(cluster);
        when(stackDtoService.getById(STACK_ID)).thenReturn(stack);
        when(gatewayConfigService.getPrimaryGatewayConfig(any())).thenReturn(gatewayConfig);
        when(stackUtil.collectGatewayNodes(any())).thenReturn(Set.of(node1, node2));
    }

    @Test
    void testBackupRdsData() throws CloudbreakOrchestratorException {
        underTest.backupRdsData(STACK_ID);
        verify(hostOrchestrator).runOrchestratorState(paramCaptor.capture());
        OrchestratorStateParams params = paramCaptor.getValue();
        assertThat(params.getState()).isEqualTo("postgresql/upgrade/backup");
        assertOtherStateParams(params);
    }

    @Test
    void testRestoreRdsData() throws CloudbreakOrchestratorException {
        underTest.restoreRdsData(STACK_ID);
        verify(hostOrchestrator).runOrchestratorState(paramCaptor.capture());
        OrchestratorStateParams params = paramCaptor.getValue();
        assertThat(params.getState()).isEqualTo("postgresql/upgrade/restore");
        assertOtherStateParams(params);
    }

    @Test
    void testValidateDbDirectorySpace()  throws CloudbreakOrchestratorException {
        when(hostOrchestrator.runCommandOnHosts(anyList(), anySet(), eq("df -k /dbfs | tail -1 | awk '{print $4}'"))).thenReturn(Map.of("fqdn1", "100000"));
        when(hostOrchestrator.runCommandOnHosts(anyList(), anySet(), eq("du -sk /dbfs/pgsql | awk '{print $1}'"))).thenReturn(Map.of("fqdn1", "10000"));
        when(gatewayConfig.getHostname()).thenReturn("fqdn1");
        underTest.validateDbDirectorySpace(STACK_ID);
        verify(hostOrchestrator).runCommandOnHosts(anyList(), eq(Set.of(node1)), eq("df -k /dbfs | tail -1 | awk '{print $4}'"));
        verify(hostOrchestrator).runCommandOnHosts(anyList(), eq(Set.of(node1)), eq("du -sk /dbfs/pgsql | awk '{print $1}'"));
    }

    @Test
    void testValidateDbDirectorySpaceWhenNotEnoughSpace()  throws CloudbreakOrchestratorException {
        when(hostOrchestrator.runCommandOnHosts(anyList(), anySet(), eq("df -k /dbfs | tail -1 | awk '{print $4}'"))).thenReturn(Map.of("fqdn1", "10000"));
        when(hostOrchestrator.runCommandOnHosts(anyList(), anySet(), eq("du -sk /dbfs/pgsql | awk '{print $1}'"))).thenReturn(Map.of("fqdn1", "100000"));
        when(gatewayConfig.getHostname()).thenReturn("fqdn1");
        CloudbreakOrchestratorException actualException = Assertions.assertThrows(CloudbreakOrchestratorException.class,
                () -> underTest.validateDbDirectorySpace(STACK_ID));
        assertThat(actualException).hasMessageStartingWith("Not enough space on attached db volume for postgres upgrade.");
    }

    @Test
    void testValidateDbDirectorySpaceWhenNoPGWResult()  throws CloudbreakOrchestratorException {
        when(hostOrchestrator.runCommandOnHosts(anyList(), anySet(), eq("df -k /dbfs | tail -1 | awk '{print $4}'"))).thenReturn(Map.of("fqdn2", "10000"));
        when(hostOrchestrator.runCommandOnHosts(anyList(), anySet(), eq("du -sk /dbfs/pgsql | awk '{print $1}'"))).thenReturn(Map.of("fqdn1", "100000"));
        when(gatewayConfig.getHostname()).thenReturn("fqdn1");
        CloudbreakOrchestratorException actualException = Assertions.assertThrows(CloudbreakOrchestratorException.class,
                () -> underTest.validateDbDirectorySpace(STACK_ID));
        assertThat(actualException).hasMessageStartingWith("Space validation on attached db volume failed.");
    }

    private void assertOtherStateParams(OrchestratorStateParams params) {
        assertThat(params.getPrimaryGatewayConfig()).isEqualTo(gatewayConfig);
        assertThat(params.getTargetHostNames()).hasSameElementsAs(Set.of("fqdn1", "fqdn2"));
        assertThat(params.getExitCriteriaModel()).isInstanceOf(ClusterDeletionBasedExitCriteriaModel.class);
        assertThat(((ClusterDeletionBasedExitCriteriaModel) params.getExitCriteriaModel()).getStackId().get()).isEqualTo(STACK_ID);
    }

}
