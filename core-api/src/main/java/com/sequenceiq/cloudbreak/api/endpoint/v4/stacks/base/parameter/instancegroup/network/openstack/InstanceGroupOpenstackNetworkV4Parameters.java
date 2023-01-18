package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.instancegroup.network.openstack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.common.mappable.MappableBase;
import com.sequenceiq.cloudbreak.common.network.NetworkConstants;
import com.sequenceiq.common.model.JsonEntity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Deprecated
public class InstanceGroupOpenstackNetworkV4Parameters extends MappableBase implements JsonEntity {

    @Schema
    private List<String> subnetIds = new ArrayList<>();

    public List<String> getSubnetIds() {
        return subnetIds;
    }

    public void setSubnetIds(List<String> subnetIds) {
        this.subnetIds = subnetIds;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        putIfValueNotNull(map, NetworkConstants.SUBNET_IDS, subnetIds);
        return map;
    }

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public CloudPlatform getCloudPlatform() {
        throw new IllegalStateException("OPENSTACK is deprecated");
    }

    @Override
    public void parse(Map<String, Object> parameters) {
        subnetIds = getStringList(parameters, NetworkConstants.SUBNET_IDS);
    }
}
