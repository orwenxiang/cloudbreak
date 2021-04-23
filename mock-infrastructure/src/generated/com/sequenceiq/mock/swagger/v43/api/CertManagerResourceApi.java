/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.16).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.sequenceiq.mock.swagger.v43.api;

import com.sequenceiq.mock.swagger.model.ApiCertificateRequest;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-23T12:05:48.864+02:00")

@Api(value = "CertManagerResource", description = "the CertManagerResource API")
@RequestMapping(value = "/{mockUuid}/api/v43")
public interface CertManagerResourceApi {

    Logger log = LoggerFactory.getLogger(CertManagerResourceApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Exchange a cert request token for a certificate.", nickname = "generateCertificate", notes = "Exchange a cert request token for a certificate. The token must be a valid token generated by the certmanager utility. The resulting certificate will containin a TLS certificate in JKS and PEM formats. It might optionally contain a private key in JKS and PEM format, corresponding to the certificate.  The tar file is designed to be consumed by the \"cm install_certs\" command to install TLS certificates on a Cloudera Manager agent host.", response = Resource.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CertManagerResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "tar file containing TLS certificate", response = Resource.class) })
    @RequestMapping(value = "/certs/generateCertificate",
        produces = { "application/octet-stream" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<Resource> generateCertificate(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = ""  )  @Valid @RequestBody ApiCertificateRequest body) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default CertManagerResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Gets the Auto-TLS truststore contents.", nickname = "getTruststore", notes = "Gets the Auto-TLS truststore contents", response = Resource.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CertManagerResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "truststore file contents", response = Resource.class) })
    @RequestMapping(value = "/certs/truststore",
        produces = { "application/octet-stream" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Resource> getTruststore(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid,@ApiParam(value = "truststore type. Must be \"JKS\" or \"PEM\".") @Valid @RequestParam(value = "type", required = false) String type) {
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
            log.warn("ObjectMapper or HttpServletRequest not configured in default CertManagerResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Gets the configured CM truststore password.", nickname = "getTruststorePassword", notes = "Gets the configured CM truststore password", response = String.class, authorizations = {
        @Authorization(value = "basic")
    }, tags={ "CertManagerResource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "truststore password", response = String.class) })
    @RequestMapping(value = "/certs/truststorePassword",
        produces = { "text/plain" }, 
        method = RequestMethod.GET)
    default ResponseEntity<String> getTruststorePassword(@ApiParam(value = "The unique id of CB cluster (works in CB test framework only)",required=true) @PathVariable("mockUuid") String mockUuid) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("", String.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type ", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CertManagerResourceApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
