/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v43.api;

import com.sequenceiq.mock.swagger.model.ApiConfigList;
import com.sequenceiq.mock.swagger.model.ApiRoleConfigGroup;
import com.sequenceiq.mock.swagger.model.ApiRoleConfigGroupList;
import com.sequenceiq.mock.swagger.model.ApiRoleList;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-23T12:05:48.864+02:00")

@Api(value = "AuthServiceRoleConfigGroupsResource", description = "the AuthServiceRoleConfigGroupsResource API")
@RequestMapping(value = "/{mockUuid}/api/v43")
public interface AuthServiceRoleConfigGroupsResourceApi {

    Logger log = LoggerFactory.getLogger(AuthServiceRoleConfigGroupsResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Returns the current revision of the config for the specified role config group in the Authentication Service.", nickname = "readConfig", notes = "Returns the current revision of the config for the specified role config group in the Authentication Service.", response = ApiConfigList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleConfigGroupsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The current configuration of the role config group.", response = ApiConfigList.class) })
    @RequestMapping(value = "/cm/authService/roleConfigGroups/{roleConfigGroupName}/config",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiConfigList> readConfig(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the role config group.",required=true) @PathVariable("roleConfigGroupName") String roleConfigGroupName,@ApiParam(value = "The view of the data to materialize, either \"summary\" or \"full\".", allowableValues = "EXPORT, EXPORT_REDACTED, FULL, FULL_WITH_HEALTH_CHECK_EXPLANATION, SUMMARY", defaultValue = "summary") @Valid @RequestParam(value = "view", required = false, defaultValue="summary") String view) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"WARNING\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  }, {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"OK\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  } ]}", ApiConfigList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleConfigGroupsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns the information for a given role config group in the Authentication Service.", nickname = "readRoleConfigGroup", notes = "Returns the information for a given role config group in the Authentication Service.", response = ApiRoleConfigGroup.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleConfigGroupsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The requested role config group.", response = ApiRoleConfigGroup.class) })
    @RequestMapping(value = "/cm/authService/roleConfigGroups/{roleConfigGroupName}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiRoleConfigGroup> readRoleConfigGroup(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the requested group.",required=true) @PathVariable("roleConfigGroupName") String roleConfigGroupName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"roleType\" : \"...\",  \"base\" : true,  \"config\" : {    \"items\" : [ {      \"name\" : \"...\",      \"value\" : \"...\",      \"required\" : true,      \"default\" : \"...\",      \"displayName\" : \"...\",      \"description\" : \"...\",      \"relatedName\" : \"...\",      \"sensitive\" : true,      \"validationState\" : \"WARNING\",      \"validationMessage\" : \"...\",      \"validationWarningsSuppressed\" : true    }, {      \"name\" : \"...\",      \"value\" : \"...\",      \"required\" : true,      \"default\" : \"...\",      \"displayName\" : \"...\",      \"description\" : \"...\",      \"relatedName\" : \"...\",      \"sensitive\" : true,      \"validationState\" : \"WARNING\",      \"validationMessage\" : \"...\",      \"validationWarningsSuppressed\" : true    } ]  },  \"displayName\" : \"...\",  \"serviceRef\" : {    \"peerName\" : \"...\",    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"serviceDisplayName\" : \"...\",    \"serviceType\" : \"...\"  }}", ApiRoleConfigGroup.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleConfigGroupsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns the information for all role config groups in the Authentication Service.", nickname = "readRoleConfigGroups", notes = "Returns the information for all role config groups in the Authentication Service.", response = ApiRoleConfigGroupList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleConfigGroupsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The list of role config groups for the given service.", response = ApiRoleConfigGroupList.class) })
    @RequestMapping(value = "/cm/authService/roleConfigGroups",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiRoleConfigGroupList> readRoleConfigGroups(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"roleType\" : \"...\",    \"base\" : true,    \"config\" : {      \"items\" : [ { }, { } ]    },    \"displayName\" : \"...\",    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    }  }, {    \"name\" : \"...\",    \"roleType\" : \"...\",    \"base\" : true,    \"config\" : {      \"items\" : [ { }, { } ]    },    \"displayName\" : \"...\",    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    }  } ]}", ApiRoleConfigGroupList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleConfigGroupsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Returns all roles in the given role config group in the Authentication Service.", nickname = "readRoles", notes = "Returns all roles in the given role config group in the Authentication Service.", response = ApiRoleList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleConfigGroupsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The roles in the role config group.", response = ApiRoleList.class) })
    @RequestMapping(value = "/cm/authService/roleConfigGroups/{roleConfigGroupName}/roles",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ApiRoleList> readRoles(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the role config group.",required=true) @PathVariable("roleConfigGroupName") String roleConfigGroupName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"type\" : \"...\",    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleState\" : \"UNKNOWN\",    \"commissionState\" : \"COMMISSIONED\",    \"healthSummary\" : \"BAD\",    \"configStalenessStatus\" : \"STALE\",    \"healthChecks\" : [ {      \"name\" : \"...\",      \"summary\" : \"GOOD\",      \"explanation\" : \"...\",      \"suppressed\" : true    }, {      \"name\" : \"...\",      \"summary\" : \"HISTORY_NOT_AVAILABLE\",      \"explanation\" : \"...\",      \"suppressed\" : true    } ],    \"haStatus\" : \"STANDBY\",    \"roleUrl\" : \"...\",    \"maintenanceMode\" : true,    \"maintenanceOwners\" : [ \"ROLE\", \"CLUSTER\" ],    \"config\" : {      \"items\" : [ { }, { } ]    },    \"roleConfigGroupRef\" : {      \"roleConfigGroupName\" : \"...\"    },    \"zooKeeperServerMode\" : \"REPLICATED_OBSERVER\",    \"entityStatus\" : \"STOPPED\",    \"tags\" : [ {      \"name\" : \"...\",      \"value\" : \"...\"    }, {      \"name\" : \"...\",      \"value\" : \"...\"    } ]  }, {    \"name\" : \"...\",    \"type\" : \"...\",    \"hostRef\" : {      \"hostId\" : \"...\",      \"hostname\" : \"...\"    },    \"serviceRef\" : {      \"peerName\" : \"...\",      \"clusterName\" : \"...\",      \"serviceName\" : \"...\",      \"serviceDisplayName\" : \"...\",      \"serviceType\" : \"...\"    },    \"roleState\" : \"NA\",    \"commissionState\" : \"DECOMMISSIONED\",    \"healthSummary\" : \"GOOD\",    \"configStalenessStatus\" : \"STALE_REFRESHABLE\",    \"healthChecks\" : [ {      \"name\" : \"...\",      \"summary\" : \"HISTORY_NOT_AVAILABLE\",      \"explanation\" : \"...\",      \"suppressed\" : true    }, {      \"name\" : \"...\",      \"summary\" : \"NOT_AVAILABLE\",      \"explanation\" : \"...\",      \"suppressed\" : true    } ],    \"haStatus\" : \"ACTIVE\",    \"roleUrl\" : \"...\",    \"maintenanceMode\" : true,    \"maintenanceOwners\" : [ \"SERVICE\", \"SERVICE\" ],    \"config\" : {      \"items\" : [ { }, { } ]    },    \"roleConfigGroupRef\" : {      \"roleConfigGroupName\" : \"...\"    },    \"zooKeeperServerMode\" : \"UNKNOWN\",    \"entityStatus\" : \"DOWN\",    \"tags\" : [ {      \"name\" : \"...\",      \"value\" : \"...\"    }, {      \"name\" : \"...\",      \"value\" : \"...\"    } ]  } ]}", ApiRoleList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleConfigGroupsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Updates the config for the given role config group in the Authentication Service.", nickname = "updateConfig", notes = "Updates the config for the given role config group in the Authentication Service.", response = ApiConfigList.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleConfigGroupsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "The updated config of the role config group.", response = ApiConfigList.class) })
    @RequestMapping(value = "/cm/authService/roleConfigGroups/{roleConfigGroupName}/config",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ApiConfigList> updateConfig(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the role config group.",required=true) @PathVariable("roleConfigGroupName") String roleConfigGroupName,@ApiParam(value = "Optional message describing the changes.") @Valid @RequestParam(value = "message", required = false) String message,@ApiParam(value = "The new config information for the group."  )  @Valid @RequestBody ApiConfigList body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"OK\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  }, {    \"name\" : \"...\",    \"value\" : \"...\",    \"required\" : true,    \"default\" : \"...\",    \"displayName\" : \"...\",    \"description\" : \"...\",    \"relatedName\" : \"...\",    \"sensitive\" : true,    \"validationState\" : \"WARNING\",    \"validationMessage\" : \"...\",    \"validationWarningsSuppressed\" : true  } ]}", ApiConfigList.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleConfigGroupsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Updates an existing role config group in the Authentication Service.", nickname = "updateRoleConfigGroup", notes = "Updates an existing role config group in the Authentication Service.", response = ApiRoleConfigGroup.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "AuthServiceRoleConfigGroupsResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Role updated role config group.", response = ApiRoleConfigGroup.class) })
    @RequestMapping(value = "/cm/authService/roleConfigGroups/{roleConfigGroupName}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<ApiRoleConfigGroup> updateRoleConfigGroup(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "The name of the group to update.",required=true) @PathVariable("roleConfigGroupName") String roleConfigGroupName,@ApiParam(value = "The optional message describing the changes.") @Valid @RequestParam(value = "message", required = false) String message,@ApiParam(value = "The updated role config group."  )  @Valid @RequestBody ApiRoleConfigGroup body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"name\" : \"...\",  \"roleType\" : \"...\",  \"base\" : true,  \"config\" : {    \"items\" : [ {      \"name\" : \"...\",      \"value\" : \"...\",      \"required\" : true,      \"default\" : \"...\",      \"displayName\" : \"...\",      \"description\" : \"...\",      \"relatedName\" : \"...\",      \"sensitive\" : true,      \"validationState\" : \"WARNING\",      \"validationMessage\" : \"...\",      \"validationWarningsSuppressed\" : true    }, {      \"name\" : \"...\",      \"value\" : \"...\",      \"required\" : true,      \"default\" : \"...\",      \"displayName\" : \"...\",      \"description\" : \"...\",      \"relatedName\" : \"...\",      \"sensitive\" : true,      \"validationState\" : \"ERROR\",      \"validationMessage\" : \"...\",      \"validationWarningsSuppressed\" : true    } ]  },  \"displayName\" : \"...\",  \"serviceRef\" : {    \"peerName\" : \"...\",    \"clusterName\" : \"...\",    \"serviceName\" : \"...\",    \"serviceDisplayName\" : \"...\",    \"serviceType\" : \"...\"  }}", ApiRoleConfigGroup.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthServiceRoleConfigGroupsResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
