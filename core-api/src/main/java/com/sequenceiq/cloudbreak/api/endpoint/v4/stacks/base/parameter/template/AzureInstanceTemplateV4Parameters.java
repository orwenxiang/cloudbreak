package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Strings;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceTemplateV4ParameterBase;
import com.sequenceiq.cloudbreak.cloud.model.InstanceTemplate;
import com.sequenceiq.cloudbreak.cloud.model.instance.AzureInstanceTemplate;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;
import com.sequenceiq.common.api.type.EncryptionType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AzureInstanceTemplateV4Parameters extends InstanceTemplateV4ParameterBase {

    @Schema(description = TemplateModelDescription.AZURE_PRIVATE_ID)
    private String privateId;

    @Schema(description = "by default false")
    private Boolean encrypted = Boolean.FALSE;

    @Schema(description = TemplateModelDescription.ENCRYPTION)
    private AzureEncryptionV4Parameters encryption;

    @Schema(description = "by default true")
    private Boolean managedDisk = Boolean.TRUE;

    @Schema(description = "by default true")
    private Boolean resourceDiskAttached = Boolean.TRUE;

    public Boolean getResourceDiskAttached() {
        return resourceDiskAttached == null ? Boolean.TRUE : resourceDiskAttached;
    }

    public void setResourceDiskAttached(Boolean resourceDiskAttached) {
        this.resourceDiskAttached = resourceDiskAttached;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public AzureEncryptionV4Parameters getEncryption() {
        return encryption;
    }

    public void setEncryption(AzureEncryptionV4Parameters encryption) {
        this.encryption = encryption;
    }

    public Boolean getManagedDisk() {
        return managedDisk;
    }

    public void setManagedDisk(Boolean managedDisk) {
        this.managedDisk = managedDisk;
    }

    public String getPrivateId() {
        return privateId;
    }

    public void setPrivateId(String privateId) {
        this.privateId = privateId;
    }

    @Override
    public void parse(Map<String, Object> parameters) {
        privateId = getParameterOrNull(parameters, "privateId");
        encrypted = getBoolean(parameters, "encrypted");
        encryption = new AzureEncryptionV4Parameters();
        encryption.setType(getBoolean(parameters, AzureInstanceTemplate.MANAGED_DISK_ENCRYPTION_WITH_CUSTOM_KEY_ENABLED) ? EncryptionType.CUSTOM : null);
        encryption.setKey(getParameterOrNull(parameters, InstanceTemplate.VOLUME_ENCRYPTION_KEY_ID));
        encryption.setDiskEncryptionSetId(getParameterOrNull(parameters, AzureInstanceTemplate.DISK_ENCRYPTION_SET_ID));
        String resourceDiskAttachedString = getParameterOrNull(parameters, AzureInstanceTemplate.RESOURCE_DISK_ATTACHED);
        // Backward compatibility is required here. Cluster which provisioned before this change can use only instancetypes which has resource disk
        if (Strings.isNullOrEmpty(resourceDiskAttachedString)) {
            resourceDiskAttached = true;
        } else {
            resourceDiskAttached = getBoolean(parameters, AzureInstanceTemplate.RESOURCE_DISK_ATTACHED);
        }
        managedDisk = getBoolean(parameters, "managedDisk");
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        putIfValueNotNull(map, "privateId", privateId);
        putIfValueNotNull(map, "encrypted", encrypted);
        if (encryption != null) {
            putIfValueNotNull(map, AzureInstanceTemplate.MANAGED_DISK_ENCRYPTION_WITH_CUSTOM_KEY_ENABLED, encryption.getType() == EncryptionType.CUSTOM);
        }
        if (resourceDiskAttached == null) {
            putIfValueNotNull(map, AzureInstanceTemplate.RESOURCE_DISK_ATTACHED, true);
        } else {
            putIfValueNotNull(map, AzureInstanceTemplate.RESOURCE_DISK_ATTACHED, resourceDiskAttached);
        }
        putIfValueNotNull(map, "managedDisk", managedDisk);
        return map;
    }

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public CloudPlatform getCloudPlatform() {
        return CloudPlatform.AZURE;
    }

    @Override
    public Map<String, Object> asSecretMap() {
        Map<String, Object> secretMap = super.asSecretMap();
        if (encryption != null) {
            putIfValueNotNull(secretMap, InstanceTemplate.VOLUME_ENCRYPTION_KEY_ID, encryption.getKey());
            putIfValueNotNull(secretMap, AzureInstanceTemplate.DISK_ENCRYPTION_SET_ID, encryption.getDiskEncryptionSetId());
        }
        return secretMap;
    }

}
