package com.sequenceiq.datalake.service.sdx.attach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sequenceiq.authorization.service.OwnerAssignmentService;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.StackV4Endpoint;
import com.sequenceiq.cloudbreak.quartz.statuschecker.service.StatusCheckerJobService;
import com.sequenceiq.cloudbreak.auth.crn.RegionAwareInternalCrnGenerator;
import com.sequenceiq.cloudbreak.auth.crn.RegionAwareInternalCrnGeneratorFactory;
import com.sequenceiq.datalake.entity.DatalakeStatusEnum;
import com.sequenceiq.datalake.entity.SdxCluster;
import com.sequenceiq.datalake.service.sdx.SdxService;
import com.sequenceiq.datalake.service.sdx.status.SdxStatusService;
import com.sequenceiq.redbeams.api.endpoint.v4.databaseserver.DatabaseServerV4Endpoint;
import com.sequenceiq.sdx.api.model.SdxDatabaseAvailabilityType;

public class SdxAttachServiceTest {
    private static final String TEST_CLUSTER_ENV_CRN = "envCrn";

    private static final String ORIGINAL_TEST_CLUSTER_NAME = "test";

    private static final String TEST_CLUSTER_NAME = "test-23123";

    private static final String TEST_CLUSTER_CRN = "testCRN";

    private static final String ORIGINAL_TEST_CLUSTER_CRN = "originalCRN";

    private static final String TEST_CLUSTER_INITIATOR_USER_CRN = "userCrn";

    private static final String TEST_CLUSTER_ACCOUNT_ID = "accountId";

    private SdxCluster testCluster;

    @Mock
    private SdxService mockSdxService;

    @Mock
    private SdxDetachNameGenerator mockSdxDetachNameGenerator;

    @Mock
    private StackV4Endpoint mockStackV4Endpoint;

    @Mock
    private DatabaseServerV4Endpoint mockRedbeamsServerEndpoint;

    @Mock
    private OwnerAssignmentService mockOwnerAssignmentService;

    @Mock
    private SdxStatusService mockSdxStatusService;

    @Mock
    private StatusCheckerJobService mockJobService;

    @Mock
    private RegionAwareInternalCrnGeneratorFactory regionAwareInternalCrnGeneratorFactory;

    @Mock
    private RegionAwareInternalCrnGenerator regionAwareInternalCrnGenerator;

    @InjectMocks
    private SdxAttachDetachUtils mockSdxAttachDetachUtils = spy(SdxAttachDetachUtils.class);

    @InjectMocks
    private SdxAttachService sdxAttachService;

    @BeforeEach
    void setUp() {
        testCluster = new SdxCluster();
        testCluster.setEnvCrn(TEST_CLUSTER_ENV_CRN);
        testCluster.setClusterName(TEST_CLUSTER_NAME);
        testCluster.setCrn(TEST_CLUSTER_CRN);
        testCluster.setOriginalCrn(ORIGINAL_TEST_CLUSTER_CRN);
        testCluster.setInitiatorUserCrn(TEST_CLUSTER_INITIATOR_USER_CRN);
        testCluster.setAccountId(TEST_CLUSTER_ACCOUNT_ID);
        testCluster.setDetached(true);
        testCluster.setDatabaseAvailabilityType(SdxDatabaseAvailabilityType.NON_HA);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReattachCluster() {
        when(mockSdxDetachNameGenerator.generateOriginalNameFromDetached(any())).thenReturn(ORIGINAL_TEST_CLUSTER_NAME);
        when(mockSdxService.save(any())).thenReturn(testCluster);

        SdxCluster reattachClusterResult = sdxAttachService.reattachCluster(testCluster);

        assertEquals(reattachClusterResult.getCrn(), ORIGINAL_TEST_CLUSTER_CRN);
        assertEquals(reattachClusterResult.getClusterName(), ORIGINAL_TEST_CLUSTER_NAME);
        assertNull(reattachClusterResult.getOriginalCrn());
        assertFalse(reattachClusterResult.isDetached());
    }

    @Test
    void testReattachClusterThrowsExceptionIfClusterNotDetached() {
        testCluster.setDetached(false);
        String errorMessage = "";

        try {
            sdxAttachService.reattachCluster(testCluster);
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
        }

        assertEquals(errorMessage, "Attempting to reattach a cluster which was not detached!");
    }

    @Test
    void testReattachStack() {
        when(regionAwareInternalCrnGenerator.getInternalCrnForServiceAsString()).thenReturn("crn");
        when(regionAwareInternalCrnGeneratorFactory.iam()).thenReturn(regionAwareInternalCrnGenerator);
        testCluster.setClusterName(ORIGINAL_TEST_CLUSTER_NAME);
        testCluster.setCrn(ORIGINAL_TEST_CLUSTER_CRN);
        sdxAttachService.reattachStack(testCluster, TEST_CLUSTER_NAME);
        verify(mockStackV4Endpoint).updateNameAndCrn(
                eq(0L), eq(TEST_CLUSTER_NAME), any(), eq(ORIGINAL_TEST_CLUSTER_NAME), eq(ORIGINAL_TEST_CLUSTER_CRN)
        );
    }

    @Test
    void testReattachExternalDatabase() throws Exception {
        when(regionAwareInternalCrnGenerator.getInternalCrnForServiceAsString()).thenReturn("crn");
        when(regionAwareInternalCrnGeneratorFactory.iam()).thenReturn(regionAwareInternalCrnGenerator);
        when(mockSdxDetachNameGenerator.generateOriginalNameFromDetached(any())).thenReturn(ORIGINAL_TEST_CLUSTER_NAME);
        when(mockSdxService.save(any())).thenReturn(testCluster);

        testCluster.setCrn(TEST_CLUSTER_CRN);
        sdxAttachService.reattachDetachedSdxCluster(testCluster);
        verify(mockRedbeamsServerEndpoint).updateClusterCrn(
                eq(TEST_CLUSTER_ENV_CRN), eq(TEST_CLUSTER_CRN), eq(ORIGINAL_TEST_CLUSTER_CRN), any()
        );
    }

    @Test
    void testSaveSdxAndAssignResourceOwnerRole() throws Exception {
        when(mockSdxService.save(any())).thenReturn(testCluster);
        testCluster.setCrn(ORIGINAL_TEST_CLUSTER_CRN);
        sdxAttachService.saveSdxAndAssignResourceOwnerRole(testCluster);
        verify(mockOwnerAssignmentService).assignResourceOwnerRoleIfEntitled(
                eq(TEST_CLUSTER_INITIATOR_USER_CRN), eq(ORIGINAL_TEST_CLUSTER_CRN), eq(TEST_CLUSTER_ACCOUNT_ID)
        );
    }

    @Test
    void testMarkAsAttached() {
        sdxAttachService.markAsAttached(testCluster);
        verify(mockSdxStatusService).setStatusForDatalake(
                eq(DatalakeStatusEnum.REQUESTED), eq("Newly attached datalake requested."), eq(testCluster)
        );
    }
}
