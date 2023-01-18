package com.sequenceiq.distrox.api.v1.distrox.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipleInstanceDeleteRequest {

    @Schema(description = ModelDescriptions.InstanceGroupModelDescription.MULTI_INSTANCE)
    private List<String> instances = new ArrayList<>();

    public List<String> getInstances() {
        return instances;
    }

    public void setInstances(List<String> instances) {
        this.instances = instances;
    }

}
