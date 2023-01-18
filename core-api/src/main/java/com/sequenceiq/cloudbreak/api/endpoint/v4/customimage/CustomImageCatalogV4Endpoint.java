package com.sequenceiq.cloudbreak.api.endpoint.v4.customimage;

import static com.sequenceiq.cloudbreak.doc.ControllerDescription.CUSTOM_IMAGE_CATALOG_V4_DESCRIPTION;
import static com.sequenceiq.cloudbreak.doc.Notes.CUSTOM_IMAGE_CATALOG_NOTES;
import static com.sequenceiq.cloudbreak.doc.Notes.IMAGE_CATALOG_NOTES;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.CREATE;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.CREATE_IN_CATALOG;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.DELETE_BY_NAME;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.DELETE_FROM_CATALOG;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.GET_BY_NAME;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.GET_BY_NAME_IN_CATALOG;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.LIST;
import static com.sequenceiq.cloudbreak.doc.OperationDescriptions.CustomImageCatalogOpDescription.UPDATE_IN_CATALOG;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.request.CustomImageCatalogV4CreateImageRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.request.CustomImageCatalogV4CreateRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.request.CustomImageCatalogV4UpdateImageRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4CreateImageResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4CreateResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4DeleteImageResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4DeleteResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4GetImageResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4GetResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4ListResponse;
import com.sequenceiq.cloudbreak.api.endpoint.v4.customimage.response.CustomImageCatalogV4UpdateImageResponse;
import com.sequenceiq.cloudbreak.auth.security.internal.AccountId;
import com.sequenceiq.cloudbreak.jerseyclient.RetryAndMetrics;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RetryAndMetrics
@Consumes(APPLICATION_JSON)
@Path("/v4/custom_image_catalogs")
@Tag(name = "/v4/customimagecatalogs", description = CUSTOM_IMAGE_CATALOG_V4_DESCRIPTION)
public interface CustomImageCatalogV4Endpoint {

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  LIST, description =  CUSTOM_IMAGE_CATALOG_NOTES,
            operationId = "listCustomImageCatalogs")
    CustomImageCatalogV4ListResponse list(@QueryParam("accountId") @AccountId String accountId);

    @GET
    @Path("name/{name}")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  GET_BY_NAME, description =  CUSTOM_IMAGE_CATALOG_NOTES,
            operationId = "getCustomImageCatalogByName")
    CustomImageCatalogV4GetResponse get(@PathParam("name") String name, @QueryParam("accountId") @AccountId String accountId);

    @POST
    @Path("")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  CREATE, description =  CUSTOM_IMAGE_CATALOG_NOTES,
            operationId = "createCustomImageCatalog")
    CustomImageCatalogV4CreateResponse create(@Valid CustomImageCatalogV4CreateRequest request, @QueryParam("accountId") @AccountId String accountId);

    @DELETE
    @Path("name/{name}")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  DELETE_BY_NAME, description =  CUSTOM_IMAGE_CATALOG_NOTES,
            operationId = "deleteCustomImageCatalogByName")
    CustomImageCatalogV4DeleteResponse delete(@PathParam("name") String name, @QueryParam("accountId") @AccountId String accountId);

    @GET
    @Path("{name}/image/{imageId}")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  GET_BY_NAME_IN_CATALOG, description =  IMAGE_CATALOG_NOTES, operationId ="getCustomImage")
    CustomImageCatalogV4GetImageResponse getCustomImage(@PathParam("name") String name, @PathParam("imageId") String imageId,
            @QueryParam("accountId") @AccountId String accountId);

    @POST
    @Path("{name}/image")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  CREATE_IN_CATALOG, description =  CUSTOM_IMAGE_CATALOG_NOTES,
            operationId = "createCustomImage")
    CustomImageCatalogV4CreateImageResponse createCustomImage(@PathParam("name") String name,
            @Valid CustomImageCatalogV4CreateImageRequest request, @QueryParam("accountId") @AccountId String accountId);

    @PUT
    @Path("{name}/image/{imageId}")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  UPDATE_IN_CATALOG, description =  IMAGE_CATALOG_NOTES,
            operationId = "updateCustomImage")
    CustomImageCatalogV4UpdateImageResponse updateCustomImage(@PathParam("name") String name, @PathParam("imageId") String imageId,
            @Valid CustomImageCatalogV4UpdateImageRequest request, @QueryParam("accountId") @AccountId String accountId);

    @DELETE
    @Path("{name}/image/{imageId}")
    @Produces(APPLICATION_JSON)
    @Operation(summary =  DELETE_FROM_CATALOG, description =  IMAGE_CATALOG_NOTES,
            operationId = "deleteCustomImage")
    CustomImageCatalogV4DeleteImageResponse deleteCustomImage(@PathParam("name") String name, @PathParam("imageId") String imageId,
            @QueryParam("accountId") @AccountId String accountId);

}
