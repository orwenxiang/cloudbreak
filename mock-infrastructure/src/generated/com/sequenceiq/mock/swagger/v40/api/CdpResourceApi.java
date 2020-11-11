/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v40.api;

import com.sequenceiq.mock.swagger.model.ApiRemoteDataContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-10-26T08:00:53.907+01:00")

@Api(value = "CdpResource", description = "the CdpResource API")
@RequestMapping(value = "/api/v40")
public interface CdpResourceApi {

    Logger log = LoggerFactory.getLogger(CdpResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Get a JSON for creating a remote data context in a Workload cluster.", nickname = "getRemoteContext", notes = "Get a JSON for creating a remote data context in a Workload cluster.", response = ApiRemoteDataContext.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CdpResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ApiRemoteDataContext.class) })
    @RequestMapping(value = "/cdp/remoteContext/byContext/{dataContextName}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiRemoteDataContext> getRemoteContext(@ApiParam(value = "The name of the DataContext.",required=true) @PathVariable("dataContextName") String dataContextName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"endPointId\" : \"...\",  \"endPoints\" : [ {    \"name\" : \"...\",    \"version\" : \"...\",    \"serviceConfigs\" : [ {      \"key\" : \"...\",      \"value\" : \"...\"    }, {      \"key\" : \"...\",      \"value\" : \"...\"    } ],    \"endPointHostList\" : [ {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    }, {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    } ],    \"serviceType\" : \"...\"  }, {    \"name\" : \"...\",    \"version\" : \"...\",    \"serviceConfigs\" : [ {      \"key\" : \"...\",      \"value\" : \"...\"    }, {      \"key\" : \"...\",      \"value\" : \"...\"    } ],    \"endPointHostList\" : [ {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    }, {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    } ],    \"serviceType\" : \"...\"  } ],  \"configs\" : [ {    \"key\" : \"...\",    \"value\" : \"...\"  }, {    \"key\" : \"...\",    \"value\" : \"...\"  } ],  \"clusterVersion\" : \"...\"}", ApiRemoteDataContext.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CdpResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Get a JSON for creating a remote data context in a Workload cluster.", nickname = "getRemoteContextByCluster", notes = "Get a JSON for creating a remote data context in a Workload cluster.", response = ApiRemoteDataContext.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CdpResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ApiRemoteDataContext.class) })
    @RequestMapping(value = "/cdp/remoteContext/byCluster/{clusterName}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiRemoteDataContext> getRemoteContextByCluster(@ApiParam(value = "The name of the DataContext.",required=true) @PathVariable("clusterName") String clusterName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"endPointId\" : \"...\",  \"endPoints\" : [ {    \"name\" : \"...\",    \"version\" : \"...\",    \"serviceConfigs\" : [ {      \"key\" : \"...\",      \"value\" : \"...\"    }, {      \"key\" : \"...\",      \"value\" : \"...\"    } ],    \"endPointHostList\" : [ {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    }, {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    } ],    \"serviceType\" : \"...\"  }, {    \"name\" : \"...\",    \"version\" : \"...\",    \"serviceConfigs\" : [ {      \"key\" : \"...\",      \"value\" : \"...\"    }, {      \"key\" : \"...\",      \"value\" : \"...\"    } ],    \"endPointHostList\" : [ {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    }, {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    } ],    \"serviceType\" : \"...\"  } ],  \"configs\" : [ {    \"key\" : \"...\",    \"value\" : \"...\"  }, {    \"key\" : \"...\",    \"value\" : \"...\"  } ],  \"clusterVersion\" : \"...\"}", ApiRemoteDataContext.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CdpResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Create or update the remote data context in the Workload cluster.", nickname = "postRemoteContext", notes = "Create or update the remote data context in the Workload cluster.", response = ApiRemoteDataContext.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CdpResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = ApiRemoteDataContext.class) })
    @RequestMapping(value = "/cdp/remoteContext",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiRemoteDataContext> postRemoteContext(@ApiParam(value = ""  )  @Valid @RequestBody ApiRemoteDataContext body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"endPointId\" : \"...\",  \"endPoints\" : [ {    \"name\" : \"...\",    \"version\" : \"...\",    \"serviceConfigs\" : [ {      \"key\" : \"...\",      \"value\" : \"...\"    }, {      \"key\" : \"...\",      \"value\" : \"...\"    } ],    \"endPointHostList\" : [ {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    }, {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    } ],    \"serviceType\" : \"...\"  }, {    \"name\" : \"...\",    \"version\" : \"...\",    \"serviceConfigs\" : [ {      \"key\" : \"...\",      \"value\" : \"...\"    }, {      \"key\" : \"...\",      \"value\" : \"...\"    } ],    \"endPointHostList\" : [ {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    }, {      \"uri\" : \"...\",      \"endPointConfigs\" : [ { }, { } ],      \"type\" : \"...\"    } ],    \"serviceType\" : \"...\"  } ],  \"configs\" : [ {    \"key\" : \"...\",    \"value\" : \"...\"  }, {    \"key\" : \"...\",    \"value\" : \"...\"  } ],  \"clusterVersion\" : \"...\"}", ApiRemoteDataContext.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CdpResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
