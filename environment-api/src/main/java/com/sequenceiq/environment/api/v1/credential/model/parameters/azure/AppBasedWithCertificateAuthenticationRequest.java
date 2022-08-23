package com.sequenceiq.environment.api.v1.credential.model.parameters.azure;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("AppBasedWithCertificateAuthenticationRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AppBasedWithCertificateAuthenticationRequest implements Serializable {

    @NotNull
    @ApiModelProperty(required = true)
    private String accessKey;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public String toString() {
        return "AppBasedWithCertificateAuthenticationRequest{" +
                "accessKey='" + accessKey + '\'' +
                '}';
    }
}
