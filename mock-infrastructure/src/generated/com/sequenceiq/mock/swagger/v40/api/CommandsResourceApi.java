/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v40.api;

import com.sequenceiq.mock.swagger.model.ApiCommand;
import java.math.BigDecimal;
import org.springframework.core.io.Resource;
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

@Api(value = "CommandsResource", description = "the CommandsResource API")
@RequestMapping(value = "/api/v40")
public interface CommandsResourceApi {

    Logger log = LoggerFactory.getLogger(CommandsResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Abort a running command.", nickname = "abortCommand", notes = "Abort a running command.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/commands/{commandId}/abort",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> abortCommand(@ApiParam(value = "The command id.",required=true) @PathVariable("commandId") BigDecimal commandId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"id\" : 12345,  \"name\" : \"...\",  \"startTime\" : \"...\",  \"endTime\" : \"...\",  \"active\" : true,  \"success\" : true,  \"resultMessage\" : \"...\",  \"resultDataUrl\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"serviceRef\" : {    \"peerName\" : \"...\",    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"serviceDisplayName\" : \"...\",    \"serviceType\" : \"...\"  },  \"roleRef\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\"  },  \"hostRef\" : {    \"hostId\" : \"...\",    \"hostname\" : \"...\"  },  \"parent\" : {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  },  \"children\" : {    \"items\" : [ {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    }, {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    } ]  },  \"canRetry\" : true}", ApiCommand.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Download a zip-compressed archive of standard error outputs for the command's one-off processes.", nickname = "getStandardError", notes = "Download a zip-compressed archive of standard error outputs for the command's one-off processes.  Log files are returned zipped together.", response = Resource.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Resource.class) })
    @RequestMapping(value = "/commands/{commandId}/logs/stderr",
        produces = { "application/octet-stream" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Resource> getStandardError(@ApiParam(value = "The command id.",required=true) @PathVariable("commandId") BigDecimal commandId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("", Resource.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Download a zip-compressed archive of standard outputs for the command's one-off processes.", nickname = "getStandardOutput", notes = "Download a zip-compressed archive of standard outputs for the command's one-off processes.  Log files are returned zipped together.", response = Resource.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Resource.class) })
    @RequestMapping(value = "/commands/{commandId}/logs/stdout",
        produces = { "application/octet-stream" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Resource> getStandardOutput(@ApiParam(value = "The command id.",required=true) @PathVariable("commandId") BigDecimal commandId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("", Resource.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Retrieve detailed information on an asynchronous command.", nickname = "readCommand", notes = "Retrieve detailed information on an asynchronous command.  <p>Cloudera Manager keeps the results and statuses of asynchronous commands, which have non-negative command IDs. On the other hand, synchronous commands complete immediately, and their results are passed back in the return object of the command execution API call. Outside of that return object, there is no way to check the result of a synchronous command.</p>", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/commands/{commandId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiCommand> readCommand(@ApiParam(value = "The command id.",required=true) @PathVariable("commandId") BigDecimal commandId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"id\" : 12345,  \"name\" : \"...\",  \"startTime\" : \"...\",  \"endTime\" : \"...\",  \"active\" : true,  \"success\" : true,  \"resultMessage\" : \"...\",  \"resultDataUrl\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"serviceRef\" : {    \"peerName\" : \"...\",    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"serviceDisplayName\" : \"...\",    \"serviceType\" : \"...\"  },  \"roleRef\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\"  },  \"hostRef\" : {    \"hostId\" : \"...\",    \"hostname\" : \"...\"  },  \"parent\" : {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  },  \"children\" : {    \"items\" : [ {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    }, {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    } ]  },  \"canRetry\" : true}", ApiCommand.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Try to rerun a command.", nickname = "retry", notes = "Try to rerun a command.", response = ApiCommand.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CommandsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = ApiCommand.class) })
    @RequestMapping(value = "/commands/{commandId}/retry",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<ApiCommand> retry(@ApiParam(value = "ID of the command that needs to be run.",required=true) @PathVariable("commandId") BigDecimal commandId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"id\" : 12345,  \"name\" : \"...\",  \"startTime\" : \"...\",  \"endTime\" : \"...\",  \"active\" : true,  \"success\" : true,  \"resultMessage\" : \"...\",  \"resultDataUrl\" : \"...\",  \"clusterRef\" : {    \"clusterName\" : \"...\",    \"displayName\" : \"...\"  },  \"serviceRef\" : {    \"peerName\" : \"...\",    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"serviceDisplayName\" : \"...\",    \"serviceType\" : \"...\"  },  \"roleRef\" : {    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"roleName\" : \"...\"  },  \"hostRef\" : {    \"hostId\" : \"...\",    \"hostname\" : \"...\"  },  \"parent\" : {    \"id\" : 12345,    \"name\" : \"...\",    \"startTime\" : \"...\",    \"endTime\" : \"...\",    \"active\" : true,    \"success\" : true,    \"resultMessage\" : \"...\",    \"resultDataUrl\" : \"...\",    \"clusterRef\" : {      \"clusterName\" : \"...\",      \"displayName\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleRef\" : {      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"roleName\" : \"...\"    },    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"parent\" : { },    \"children\" : {      \"items\" : [ { }, { } ]    },    \"canRetry\" : true  },  \"children\" : {    \"items\" : [ {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    }, {      \"id\" : 12345,      \"name\" : \"...\",      \"startTime\" : \"...\",      \"endTime\" : \"...\",      \"active\" : true,      \"success\" : true,      \"resultMessage\" : \"...\",      \"resultDataUrl\" : \"...\",      \"clusterRef\" : { },      \"serviceRef\" : { },      \"roleRef\" : { },      \"hostRef\" : { },      \"parent\" : { },      \"children\" : { },      \"canRetry\" : true    } ]  },  \"canRetry\" : true}", ApiCommand.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CommandsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
