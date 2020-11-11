/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v40.api;

import com.sequenceiq.mock.swagger.model.ApiBulkCommandList;
import com.sequenceiq.mock.swagger.model.ApiRoleNameList;
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

@Api(value = "AuthServiceRoleCommandsResource", description = "the AuthServiceRoleCommandsResource API")
@RequestMapping(value = "/api/v40")
public interface AuthServiceRoleCommandsResourceApi {

    Logger log = LoggerFactory.getLogger(AuthServiceRoleCommandsResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Restart a set of Authentication Service roles.", nickname = "restartCommand", notes = "Restart a set of Authentication Service roles.", response = ApiBulkCommandList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleCommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = ApiBulkCommandList.class) })
    @RequestMapping(value = "/cm/authService/roleCommands/restart",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiBulkCommandList> restartCommand(@ApiParam(value = "The roles to restart."  )  @Valid @RequestBody ApiRoleNameList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"errors\" : [ \"...\", \"...\" ],  \"items\" : [ {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  }, {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  } ]}", ApiBulkCommandList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleCommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Start a set of Authentication Service roles.", nickname = "startCommand", notes = "Start a set of Authentication Service roles.", response = ApiBulkCommandList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleCommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = ApiBulkCommandList.class) })
    @RequestMapping(value = "/cm/authService/roleCommands/start",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiBulkCommandList> startCommand(@ApiParam(value = "The roles to start."  )  @Valid @RequestBody ApiRoleNameList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"errors\" : [ \"...\", \"...\" ],  \"items\" : [ {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  }, {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  } ]}", ApiBulkCommandList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleCommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Stop a set of Authentication Service roles.", nickname = "stopCommand", notes = "Stop a set of Authentication Service roles.", response = ApiBulkCommandList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleCommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = ApiBulkCommandList.class) })
    @RequestMapping(value = "/cm/authService/roleCommands/stop",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<ApiBulkCommandList> stopCommand(@ApiParam(value = "The roles to stop."  )  @Valid @RequestBody ApiRoleNameList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"errors\" : [ \"...\", \"...\" ],  \"items\" : [ {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  }, {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  } ]}", ApiBulkCommandList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleCommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
