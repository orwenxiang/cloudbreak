package com.sequenceiq.cloudbreak.service.upgrade;

import static com.sequenceiq.common.model.ImageCatalogPlatform.imageCatalogPlatform;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.StackType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.Status;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.InternalUpgradeSettings;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.tags.upgrade.UpgradeV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.image.ImageComponentVersions;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.image.ImageInfoV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.upgrade.UpgradeV4Response;
import com.sequenceiq.cloudbreak.auth.altus.EntitlementService;
import com.sequenceiq.cloudbreak.cloud.model.catalog.Image;
import com.sequenceiq.cloudbreak.common.exception.BadRequestException;
import com.sequenceiq.cloudbreak.core.CloudbreakImageNotFoundException;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.StackStatus;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceMetaData;
import com.sequenceiq.cloudbreak.service.cluster.ClusterRepairService;
import com.sequenceiq.cloudbreak.service.cluster.model.HostGroupName;
import com.sequenceiq.cloudbreak.service.cluster.model.RepairValidation;
import com.sequenceiq.cloudbreak.service.cluster.model.Result;
import com.sequenceiq.cloudbreak.service.image.ModelImageTestBuilder;
import com.sequenceiq.cloudbreak.service.stack.InstanceMetaDataService;
import com.sequenceiq.cloudbreak.service.upgrade.image.ClusterUpgradeImageFilter;
import com.sequenceiq.cloudbreak.service.upgrade.image.ImageFilterParams;
import com.sequenceiq.cloudbreak.service.upgrade.image.ImageFilterResult;
import com.sequenceiq.cloudbreak.workspace.model.Tenant;
import com.sequenceiq.cloudbreak.workspace.model.User;
import com.sequenceiq.cloudbreak.workspace.model.Workspace;

@ExtendWith(MockitoExtension.class)
public class ClusterUpgradeAvailabilityServiceTest {

    private static final String ACCOUNT_ID = "cloudera";

    private static final String CATALOG_NAME = "catalog";

    private static final String FALLBACK_CATALOG_URL = "/images/fallback";

    private static final String CURRENT_IMAGE_ID = "16ad7759-83b1-42aa-aadf-0e3a6e7b5444";

    private static final String IMAGE_ID = "image-id-first";

    private static final String IMAGE_ID_LAST = "image-id-last";

    private static final String ANOTHER_IMAGE_ID = "another-image-id";

    private static final String MATCHING_TARGET_RUNTIME = "7.0.2";

    private static final String ANOTHER_TARGET_RUNTIME = "7.2.0";

    private static final String V_7_0_3 = "7.0.3";

    private static final String V_7_0_2 = "7.0.2";

    private static final StackType DATALAKE_STACK_TYPE = StackType.DATALAKE;

    private static final Long WORKSPACE_ID = 1L;

    private static final String CLOUD_PLATFORM = "AWS";

    private static final String REGION = "region";

    private static final long STACK_ID = 2L;

    private static final InternalUpgradeSettings INTERNAL_UPGRADE_SETTINGS = new InternalUpgradeSettings(false, true, true);

    @InjectMocks
    private ClusterUpgradeAvailabilityService underTest;

    @Mock
    private CurrentImageRetrieverService currentImageRetrieverService;

    @Mock
    private ClusterUpgradeImageFilter clusterUpgradeImageFilter;

    @Mock
    private UpgradeOptionsResponseFactory upgradeOptionsResponseFactory;

    @Mock
    private ClusterRepairService clusterRepairService;

    @Mock
    private ImageFilterParamsFactory imageFilterParamsFactory;

    @Mock
    private EntitlementService entitlementService;

    @Mock
    private InstanceMetaDataService instanceMetaDataService;

    private boolean lockComponents;

    private final Map<String, String> activatedParcels = new HashMap<>();

    @Test
    public void testCheckForUpgradesByNameAndSomeInstancesAreStopped() throws CloudbreakImageNotFoundException {
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), StackType.WORKLOAD);
        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImage();
        Image properImage = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);
        ImageFilterParams imageFilterParams = createImageFilterParams(stack, currentImage);
        UpgradeV4Response response = new UpgradeV4Response();

        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        ImageFilterResult filteredImages = createFilteredImages(properImage);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, CATALOG_NAME, imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, stack.getCloudPlatform(), stack.getRegion(),
                currentImage.getImageCatalogName())).thenReturn(response);
        when(instanceMetaDataService.anyInstanceStopped(stack.getId())).thenReturn(true);

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertEquals("Cannot upgrade cluster because there is stopped instance.", actual.getReason());
        assertEquals(response, actual);
    }

    @Test
    public void testCheckForUpgradesByNameShouldReturnImagesWhenThereAreAvailableImagesUsingImageCatalogByName() throws CloudbreakImageNotFoundException {
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), StackType.WORKLOAD);
        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImage();
        Result result = Mockito.mock(Result.class);
        Image properImage = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);
        ImageFilterParams imageFilterParams = createImageFilterParams(stack, currentImage);
        UpgradeV4Response response = new UpgradeV4Response();

        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        when(clusterRepairService.repairWithDryRun(stack.getId())).thenReturn(result);
        when(result.isError()).thenReturn(false);
        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        ImageFilterResult filteredImages = createFilteredImages(properImage);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, CATALOG_NAME, imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, stack.getCloudPlatform(), stack.getRegion(),
                currentImage.getImageCatalogName())).thenReturn(response);
        when(instanceMetaDataService.anyInstanceStopped(stack.getId())).thenReturn(false);

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertEquals(response, actual);
    }

    @Test
    public void testCheckForUpgradesByNameShouldReturnImagesFromFallbackCatalogWhenThereAreAvailableImagesButImageCatalogByNameNotFound()
            throws CloudbreakImageNotFoundException {
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), StackType.WORKLOAD);
        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImage();
        Result result = Mockito.mock(Result.class);
        Image properImage = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);
        ImageFilterParams imageFilterParams = createImageFilterParams(stack, currentImage);
        UpgradeV4Response response = new UpgradeV4Response();

        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        when(clusterRepairService.repairWithDryRun(stack.getId())).thenReturn(result);
        when(result.isError()).thenReturn(false);
        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        ImageFilterResult filteredImages = createFilteredImages(properImage);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, CATALOG_NAME, imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, stack.getCloudPlatform(), stack.getRegion(),
                currentImage.getImageCatalogName())).thenReturn(response);
        when(instanceMetaDataService.anyInstanceStopped(stack.getId())).thenReturn(false);

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertEquals(response, actual);
    }

    @Test
    public void testCheckForUpgradesByNameShouldReturnImagesWhenThereAreAvailableImagesAndRepairNotChecked()
            throws CloudbreakImageNotFoundException {
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), StackType.WORKLOAD);
        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImage();
        Image properImage = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);
        UpgradeV4Response response = new UpgradeV4Response();

        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        ImageFilterResult filteredImages = createFilteredImages(properImage);
        ImageFilterParams imageFilterParams = createImageFilterParams(stack, currentImage);
        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, CATALOG_NAME, imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, stack.getCloudPlatform(), stack.getRegion(),
                currentImage.getImageCatalogName())).thenReturn(response);
        when(instanceMetaDataService.anyInstanceStopped(stack.getId())).thenReturn(false);

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, false, INTERNAL_UPGRADE_SETTINGS, false);

        assertEquals(response, actual);
        verifyNoInteractions(clusterRepairService);
    }

    @Test
    public void testGetImagesToUpgradeShouldReturnEmptyListWhenCurrentImageIsNotFound() throws CloudbreakImageNotFoundException {
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), DATALAKE_STACK_TYPE);
        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenThrow(new CloudbreakImageNotFoundException("Image not found."));

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertNull(actual.getCurrent());
        assertNull(actual.getUpgradeCandidates());
        assertEquals("Failed to retrieve image due to Image not found.", actual.getReason());
    }

    @Test
    public void testGetImagesToUpgradeShouldReturnListWhenTheClusterIsNotAvailable() throws CloudbreakImageNotFoundException {
        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImage();
        Image properImage = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo));
        Stack stack = createStack(createStackStatus(Status.CREATE_FAILED), StackType.WORKLOAD);
        ImageFilterParams imageFilterParams = createImageFilterParams(stack, currentImage);

        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        ImageFilterResult filteredImages = createFilteredImages(properImage);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, CATALOG_NAME, imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, stack.getCloudPlatform(), stack.getRegion(),
                currentImage.getImageCatalogName())).thenReturn(response);

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertNull(actual.getCurrent());
        assertEquals(1, actual.getUpgradeCandidates().size());
        assertEquals("Cannot upgrade cluster because it is in CREATE_FAILED state.", actual.getReason());
    }

    @Test
    public void testGetImagesToUpgradeShouldReturnEmptyListWhenTheClusterIsNotRepairable() throws CloudbreakImageNotFoundException {

        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImage();
        Result<Map<HostGroupName, Set<InstanceMetaData>>, RepairValidation> result = mock(Result.class);
        Image properImage = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);

        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo));
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), StackType.WORKLOAD);
        RepairValidation repairValidation = mock(RepairValidation.class);
        ImageFilterParams imageFilterParams = createImageFilterParams(stack, currentImage);

        when(clusterRepairService.repairWithDryRun(stack.getId())).thenReturn(result);
        when(clusterRepairService.repairWithDryRun(stack.getId())).thenReturn(result);
        when(result.isError()).thenReturn(true);
        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        ImageFilterResult filteredImages = createFilteredImages(properImage);
        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, CATALOG_NAME, imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, stack.getCloudPlatform(), stack.getRegion(),
                currentImage.getImageCatalogName())).thenReturn(response);
        String validationError = "External RDS is not attached.";
        when(result.getError()).thenReturn(repairValidation);
        when(repairValidation.getValidationErrors()).thenReturn(Collections.singletonList(validationError));
        when(instanceMetaDataService.anyInstanceStopped(stack.getId())).thenReturn(false);

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertNull(actual.getCurrent());
        assertEquals(1, actual.getUpgradeCandidates().size());
        assertEquals(validationError, actual.getReason());
    }

    @Test
    public void testFilterUpgradeOptionsUpgradeRequestEmpty() {
        UpgradeV4Request request = new UpgradeV4Request();
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));

        underTest.filterUpgradeOptions(response, request, true);

        assertEquals(1, response.getUpgradeCandidates().size());
        assertEquals(IMAGE_ID_LAST, response.getUpgradeCandidates().get(0).getImageId());
    }

    @Test
    public void testFilterUpgradeOptionsImageIdValid() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setImageId(IMAGE_ID);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));
        ImageInfoV4Response currentImageInfo = new ImageInfoV4Response();
        currentImageInfo.setImageId(CURRENT_IMAGE_ID);
        response.setCurrent(currentImageInfo);

        underTest.filterUpgradeOptions(response, request, true);

        assertEquals(1, response.getUpgradeCandidates().size());
        assertEquals(IMAGE_ID, response.getUpgradeCandidates().get(0).getImageId());
    }

    @Test
    public void testFilterUpgradeOptionsImageIdInvalid() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setImageId(ANOTHER_IMAGE_ID);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));
        ImageInfoV4Response currentImageInfo = new ImageInfoV4Response();
        currentImageInfo.setImageId(CURRENT_IMAGE_ID);
        response.setCurrent(currentImageInfo);

        Exception e = assertThrows(BadRequestException.class, () -> underTest.filterUpgradeOptions(response, request, true));
        assertEquals("The given image (another-image-id) is not eligible for the cluster upgrade. "
                + "Please choose an id from the following image(s): image-id-first,image-id-last", e.getMessage());
    }

    @Test
    public void testFilterUpgradeOptionsImageIdIsValidWhenTheCurrentImageIsSentInTheRequest() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setImageId(CURRENT_IMAGE_ID);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response currentImageInfo = new ImageInfoV4Response();
        currentImageInfo.setImageId(CURRENT_IMAGE_ID);
        currentImageInfo.setCreated(1L);
        currentImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo, currentImageInfo));
        response.setCurrent(currentImageInfo);

        underTest.filterUpgradeOptions(response, request, true);

        assertEquals(1, response.getUpgradeCandidates().size());
        assertEquals(1, response.getUpgradeCandidates().stream().map(ImageInfoV4Response::getImageId).collect(Collectors.toSet()).size());
        assertTrue(response.getUpgradeCandidates().contains(currentImageInfo));
    }

    @Test
    public void testFilterUpgradeOptionsRuntimeValid() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setRuntime(MATCHING_TARGET_RUNTIME);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));

        underTest.filterUpgradeOptions(response, request, true);

        assertEquals(2, response.getUpgradeCandidates().size());
        assertEquals(2, response.getUpgradeCandidates().stream().map(ImageInfoV4Response::getImageId).collect(Collectors.toSet()).size());
    }

    @Test
    public void testFilterUpgradeOptionsRuntimeInvalid() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setRuntime(ANOTHER_TARGET_RUNTIME);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));

        Exception e = assertThrows(BadRequestException.class, () -> underTest.filterUpgradeOptions(response, request, true));
        assertEquals("There is no image eligible for the cluster upgrade with runtime: 7.2.0. "
                + "Please choose a runtime from the following: 7.0.2", e.getMessage());
    }

    @Test
    public void testFilterUpgradeOptionsLockComponents() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setLockComponents(true);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));
        underTest.filterUpgradeOptions(response, request, true);

        assertEquals(2, response.getUpgradeCandidates().size());
        assertTrue(response.getUpgradeCandidates().stream().map(ImageInfoV4Response::getImageId).anyMatch(id -> id.equals(IMAGE_ID_LAST)));
        assertTrue(response.getUpgradeCandidates().stream().map(ImageInfoV4Response::getImageId).anyMatch(id -> id.equals(IMAGE_ID)));
    }

    @Test
    public void testFilterUpgradeOptionsDefaultCase() {
        UpgradeV4Request request = new UpgradeV4Request();
        request.setDryRun(true);
        UpgradeV4Response response = new UpgradeV4Response();
        ImageInfoV4Response imageInfo = new ImageInfoV4Response();
        imageInfo.setImageId(IMAGE_ID);
        imageInfo.setCreated(1L);
        imageInfo.setComponentVersions(createExpectedPackageVersions());
        ImageInfoV4Response lastImageInfo = new ImageInfoV4Response();
        lastImageInfo.setImageId(IMAGE_ID_LAST);
        lastImageInfo.setCreated(2L);
        lastImageInfo.setComponentVersions(createExpectedPackageVersions());
        response.setUpgradeCandidates(List.of(imageInfo, lastImageInfo));
        underTest.filterUpgradeOptions(response, request, true);

        assertEquals(2, response.getUpgradeCandidates().size());
        assertEquals(2, response.getUpgradeCandidates().stream().map(ImageInfoV4Response::getImageId).collect(Collectors.toSet()).size());
    }

    @Test
    public void testFilterUpgradeOptionsFromCustomImageCatalog() throws CloudbreakImageNotFoundException {
        Stack stack = createStack(createStackStatus(Status.AVAILABLE), StackType.WORKLOAD);
        com.sequenceiq.cloudbreak.cloud.model.Image currentImage = createCurrentImageWithoutImageCatalogUrl();
        ImageFilterParams imageFilterParams = mock(ImageFilterParams.class);
        Image imageAvailableForUpgrade = mock(com.sequenceiq.cloudbreak.cloud.model.catalog.Image.class);
        ImageFilterResult filteredImages = createFilteredImages(imageAvailableForUpgrade);
        UpgradeV4Response upgradeResponse = mock(UpgradeV4Response.class);

        when(currentImageRetrieverService.retrieveCurrentModelImage(stack)).thenReturn(currentImage);
        when(imageFilterParamsFactory.create(currentImage, lockComponents, stack, INTERNAL_UPGRADE_SETTINGS, false)).thenReturn(imageFilterParams);
        when(clusterUpgradeImageFilter.filter(ACCOUNT_ID, WORKSPACE_ID, currentImage.getImageCatalogName(), imageFilterParams)).thenReturn(filteredImages);
        when(upgradeOptionsResponseFactory.createV4Response(currentImage, filteredImages, CLOUD_PLATFORM, REGION, CATALOG_NAME)).thenReturn(upgradeResponse);
        when(upgradeResponse.getReason()).thenReturn("done");

        UpgradeV4Response actual = underTest.checkForUpgradesByName(stack, lockComponents, true, INTERNAL_UPGRADE_SETTINGS, false);

        assertEquals(upgradeResponse, actual);
    }

    private StackStatus createStackStatus(Status status) {
        StackStatus stackStatus = new StackStatus();
        stackStatus.setStatus(status);
        return stackStatus;
    }

    private Stack createStack(StackStatus stackStatus, StackType stackType) {
        Cluster cluster = new Cluster();
        cluster.setId(1L);
        Stack stack = new Stack();
        stack.setId(2L);
        stack.setCloudPlatform(CLOUD_PLATFORM);
        stack.setStackStatus(stackStatus);
        stack.setCluster(cluster);
        stack.setType(stackType);
        stack.setRegion(REGION);
        stack.setResourceCrn("crn:cdp:datahub:us-west-1:" + ACCOUNT_ID + ":cluster:cluster");
        Tenant t = new Tenant();
        t.setName(ACCOUNT_ID);
        User creator = new User();
        creator.setTenant(t);
        stack.setCreator(creator);
        Workspace workspace = new Workspace();
        workspace.setId(WORKSPACE_ID);
        stack.setWorkspace(workspace);
        return stack;
    }

    private com.sequenceiq.cloudbreak.cloud.model.Image createCurrentImage() {
        return ModelImageTestBuilder.builder()
                .withImageId(CURRENT_IMAGE_ID)
                .withImageCatalogName(CATALOG_NAME)
                .withImageCatalogUrl(FALLBACK_CATALOG_URL)
                .build();
    }

    private com.sequenceiq.cloudbreak.cloud.model.Image createCurrentImageWithoutImageCatalogUrl() {
        return ModelImageTestBuilder.builder()
                .withImageId(CURRENT_IMAGE_ID)
                .withImageCatalogName(CATALOG_NAME)
                .build();
    }

    private ImageFilterResult createFilteredImages(com.sequenceiq.cloudbreak.cloud.model.catalog.Image image) {
        return new ImageFilterResult(List.of(image), null);
    }

    private ImageComponentVersions createExpectedPackageVersions() {
        ImageComponentVersions imageComponentVersions = new ImageComponentVersions();
        imageComponentVersions.setCm(V_7_0_3);
        imageComponentVersions.setCdp(V_7_0_2);
        return imageComponentVersions;
    }

    private ImageFilterParams createImageFilterParams(Stack stack, com.sequenceiq.cloudbreak.cloud.model.Image currentImage) {
        return new ImageFilterParams(currentImage, null, lockComponents, activatedParcels,
                stack.getType(), null, STACK_ID, INTERNAL_UPGRADE_SETTINGS, imageCatalogPlatform(CLOUD_PLATFORM), CLOUD_PLATFORM, REGION, false);
    }
}
