package com.sequenceiq.it.cloudbreak.testcase.e2e.sdx;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.recipes.requests.RecipeV4Type.POST_CLOUDERA_MANAGER_START;
import static com.sequenceiq.freeipa.rotation.FreeIpaRotationAdditionalParameters.CLUSTER_NAME;
import static com.sequenceiq.it.cloudbreak.cloud.HostGroupType.IDBROKER;
import static com.sequenceiq.it.cloudbreak.cloud.HostGroupType.MASTER;
import static com.sequenceiq.it.cloudbreak.context.RunningParameter.key;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_CB_CM_ADMIN_PASSWORD;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_CM_DB_PASSWORD;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_CM_INTERMEDIATE_CA_CERT;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_CM_SERVICE_DB_PASSWORD;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_DATABASE_ROOT_PASSWORD;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_GATEWAY_CERT;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_IDBROKER_CERT;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_LDAP_BIND_PASSWORD;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_MGMT_CM_ADMIN_PASSWORD;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_SALT_BOOT_SECRETS;
import static com.sequenceiq.sdx.rotation.DatalakeSecretType.DATALAKE_USER_KEYPAIR;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.google.common.collect.Sets;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.it.cloudbreak.assertion.datalake.RecipeTestAssertion;
import com.sequenceiq.it.cloudbreak.client.RecipeTestClient;
import com.sequenceiq.it.cloudbreak.client.SdxTestClient;
import com.sequenceiq.it.cloudbreak.client.StackTestClient;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.ClouderaManagerTestDto;
import com.sequenceiq.it.cloudbreak.dto.ClusterTestDto;
import com.sequenceiq.it.cloudbreak.dto.ImageSettingsTestDto;
import com.sequenceiq.it.cloudbreak.dto.InstanceGroupTestDto;
import com.sequenceiq.it.cloudbreak.dto.recipe.RecipeTestDto;
import com.sequenceiq.it.cloudbreak.dto.sdx.SdxInternalTestDto;
import com.sequenceiq.it.cloudbreak.dto.stack.StackTestDto;
import com.sequenceiq.it.cloudbreak.dto.telemetry.TelemetryTestDto;
import com.sequenceiq.it.cloudbreak.exception.TestFailException;
import com.sequenceiq.it.cloudbreak.util.RecipeUtil;
import com.sequenceiq.it.cloudbreak.util.SdxUtil;
import com.sequenceiq.it.cloudbreak.util.SecretRotationCheckUtil;
import com.sequenceiq.it.cloudbreak.util.ssh.SshJUtil;
import com.sequenceiq.sdx.api.model.SdxClusterStatusResponse;
import com.sequenceiq.sdx.api.model.SdxDatabaseAvailabilityType;
import com.sequenceiq.sdx.api.model.SdxDatabaseRequest;
import com.sequenceiq.sdx.rotation.DatalakeSecretType;

public class InternalSdxRepairWithRecipeTest extends PreconditionSdxE2ETest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalSdxRepairWithRecipeTest.class);

    private static final String FILEPATH = "/post-cm-start";

    private static final String FILENAME = "post-cm-start";

    private static final String MASTER_INSTANCEGROUP = "master";

    private static final String IDBROKER_INSTANCEGROUP = "idbroker";

    private static final String TELEMETRY = "telemetry";

    private String sdxInternal;

    @Inject
    private SdxTestClient sdxTestClient;

    @Inject
    private RecipeTestClient recipeTestClient;

    @Inject
    private StackTestClient stackTestClient;

    @Inject
    private SshJUtil sshJUtil;

    @Inject
    private SdxUtil sdxUtil;

    @Inject
    private RecipeUtil recipeUtil;

    @Inject
    private SecretRotationCheckUtil secretRotationCheckUtil;

    @Test(dataProvider = TEST_CONTEXT)
    @Description(
            given = "there is an environment with SDX in available state",
            when = "in case of AWS provider secrets are getting rotated before " +
                    "recovery called on the IDBROKER and MASTER host group, " +
                    "where the instance had been stopped",
            then = "all the actions (secret rotatioin then recovery for AWS OR just recovery) " +
                    "should be successful, the cluster should be available"
    )
    public void testIDBRokerAndMasterRepairWithRecipeFile(TestContext testContext) {
        String cloudProvider = commonCloudProperties().getCloudProvider();

        if (CloudPlatform.AWS.equalsIgnoreCase(cloudProvider)) {
            createAutoTLSSdx(testContext);
            secretRotation(testContext);
            multiRepairThenValidate(testContext);
        } else {
            createAutoTLSSdx(testContext);
            multiRepairThenValidate(testContext);
        }
    }

    private void createAutoTLSSdx(TestContext testContext) {
        sdxInternal = resourcePropertyProvider().getName();
        String cluster = resourcePropertyProvider().getName();
        String clouderaManager = resourcePropertyProvider().getName();
        String recipeName = resourcePropertyProvider().getName();
        String stack = resourcePropertyProvider().getName();
        String imageSettings = resourcePropertyProvider().getName();

        SdxDatabaseRequest sdxDatabaseRequest = new SdxDatabaseRequest();
        sdxDatabaseRequest.setAvailabilityType(SdxDatabaseAvailabilityType.NON_HA);

        String selectedImageID = getLatestPrewarmedImageId(testContext);

        testContext
                .given(imageSettings, ImageSettingsTestDto.class)
                .withImageCatalog(commonCloudProperties().getImageCatalogName())
                .withImageId(selectedImageID)
                .given(clouderaManager, ClouderaManagerTestDto.class)
                .given(cluster, ClusterTestDto.class)
                .withBlueprintName(getDefaultSDXBlueprintName())
                .withValidateBlueprint(Boolean.FALSE)
                .withClouderaManager(clouderaManager)
                .given(RecipeTestDto.class)
                .withName(recipeName)
                .withContent(recipeUtil.generatePostCmStartRecipeContent(applicationContext))
                .withRecipeType(POST_CLOUDERA_MANAGER_START)
                .when(recipeTestClient.createV4())
                .given(MASTER_INSTANCEGROUP, InstanceGroupTestDto.class)
                .withHostGroup(MASTER)
                .withNodeCount(1)
                .withRecipes(recipeName)
                .given(IDBROKER_INSTANCEGROUP, InstanceGroupTestDto.class)
                .withHostGroup(IDBROKER)
                .withNodeCount(1)
                .withRecipes(recipeName)
                .given(stack, StackTestDto.class)
                .withCluster(cluster)
                .withInstanceGroups(MASTER_INSTANCEGROUP, IDBROKER_INSTANCEGROUP)
                .withImageSettings(imageSettings)
                .given(TELEMETRY, TelemetryTestDto.class)
                .withLogging()
                .withReportClusterLogs()
                .given(sdxInternal, SdxInternalTestDto.class)
                .withCloudStorage(getCloudStorageRequest(testContext))
                .withDatabase(sdxDatabaseRequest)
                .withAutoTls()
                .withStackRequest(key(cluster), key(stack))
                .withTelemetry(TELEMETRY)
                .when(sdxTestClient.createInternal(), key(sdxInternal))
                .await(SdxClusterStatusResponse.RUNNING, key(sdxInternal))
                .awaitForHealthyInstances()
                .then(RecipeTestAssertion.validateFilesOnHost(List.of(MASTER.getName(), IDBROKER.getName()), FILEPATH, FILENAME, 1, sshJUtil))
                .then((tc, dto, client) -> {
                    if (!StringUtils.equalsIgnoreCase(dto.getResponse().getStackV4Response().getImage().getId(), selectedImageID)) {
                        throw new TestFailException(String.format("The datalake image Id (%s) do NOT match with the selected pre-warmed image Id: '%s'!",
                                dto.getResponse().getStackV4Response().getImage().getId(), selectedImageID));
                    }
                    return dto;
                });
    }

    private void secretRotation(TestContext testContext) {
        String clusterName = testContext.given(sdxInternal, SdxInternalTestDto.class).getResponse().getName();
        Set<DatalakeSecretType> secretTypes = Sets.newHashSet();
        secretTypes.addAll(Set.of(
                DATALAKE_USER_KEYPAIR,
                DATALAKE_IDBROKER_CERT,
                DATALAKE_SALT_BOOT_SECRETS,
                DATALAKE_MGMT_CM_ADMIN_PASSWORD,
                DATALAKE_CB_CM_ADMIN_PASSWORD,
                DATALAKE_DATABASE_ROOT_PASSWORD,
                DATALAKE_CM_DB_PASSWORD,
                DATALAKE_CM_SERVICE_DB_PASSWORD,
                DATALAKE_CM_INTERMEDIATE_CA_CERT));
        if (testContext.given(sdxInternal, SdxInternalTestDto.class).govCloud()) {
            // CB-24849
            secretTypes.add(DATALAKE_GATEWAY_CERT);
        }
        testContext
                .given(sdxInternal, SdxInternalTestDto.class)
                .when(sdxTestClient.describeInternal(), key(sdxInternal))
                .when(sdxTestClient.rotateSecret(secretTypes))
                .awaitForFlow()
                .when(sdxTestClient.rotateSecret(Set.of(DATALAKE_LDAP_BIND_PASSWORD),
                        Map.of(CLUSTER_NAME.name(), clusterName)))
                .awaitForFlow()
                .then((tc, testDto, client) -> {
                    secretRotationCheckUtil.checkLdapLogin(testDto.getCrn(), client);
                    secretRotationCheckUtil.checkSSHLoginWithNewKeys(testDto.getCrn(), client);
                    return testDto;
                });
    }

    private void multiRepairThenValidate(TestContext testContext) {
        testContext
                .given(sdxInternal, SdxInternalTestDto.class)
                .when(sdxTestClient.describeInternal(), key(sdxInternal))
                .then((tc, testDto, client) -> {
                    List<String> instanceIdsToStop = sdxUtil.getInstanceIds(testDto, client, MASTER.getName());
                    instanceIdsToStop.addAll(sdxUtil.getInstanceIds(testDto, client, IDBROKER.getName()));
                    getCloudFunctionality(tc).stopInstances(testDto.getName(), instanceIdsToStop);
                    return testDto;
                })
                .awaitForStoppedInstances()
                .when(sdxTestClient.repairInternal(MASTER.getName(), IDBROKER.getName()), key(sdxInternal))
                .await(SdxClusterStatusResponse.REPAIR_IN_PROGRESS, key(sdxInternal).withWaitForFlow(Boolean.FALSE))
                .await(SdxClusterStatusResponse.RUNNING, key(sdxInternal))
                .awaitForHealthyInstances()
                .then(RecipeTestAssertion.validateFilesOnHost(List.of(MASTER.getName(), IDBROKER.getName()), FILEPATH, FILENAME, 1, sshJUtil))
                .validate();
    }
}
