package com.sequenceiq.redbeams.api.endpoint.v4.operation;

import static com.sequenceiq.redbeams.doc.OperationDescriptions.OperationOpDescriptions.GET_OPERATIONS;
import static com.sequenceiq.redbeams.doc.OperationDescriptions.OperationOpDescriptions.NOTES;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sequenceiq.cloudbreak.jerseyclient.RetryAndMetrics;
import com.sequenceiq.flow.api.model.operation.OperationView;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Path("/v4/operation")
@RetryAndMetrics
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "/v4/operation", description = "Get flow step progression")
public interface OperationV4Endpoint {

    @GET
    @Path("/resource/crn/{resourceCrn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  GET_OPERATIONS, description =  NOTES,
            operationId = "getRedbeamsOperationProgressByResourceCrn")
    OperationView getRedbeamsOperationProgressByResourceCrn(@PathParam("resourceCrn") String resourceCrn,
            @DefaultValue("false") @QueryParam("detailed") boolean detailed);
}
