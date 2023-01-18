package com.sequenceiq.distrox.api.v1.distrox.model.cluster.repository;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterManagerRepositoryDescription;
import com.sequenceiq.common.model.JsonEntity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RepositoryV1Request implements JsonEntity {

    @NotNull
    @Schema(description = ClusterManagerRepositoryDescription.VERSION, required = true)
    private String version;

    @Schema(description = ClusterManagerRepositoryDescription.BASE_URL)
    private String baseUrl;

    @Schema(description = ClusterManagerRepositoryDescription.REPO_GPG_KEY)
    private String gpgKeyUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getGpgKeyUrl() {
        return gpgKeyUrl;
    }

    public void setGpgKeyUrl(String gpgKeyUrl) {
        this.gpgKeyUrl = gpgKeyUrl;
    }
}
