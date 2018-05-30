package com.sequenceiq.cloudbreak.blueprint.filesystem.query;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigQueryEntry {

    private String propertyName;

    private String description;

    private String defaultPath;

    private String relatedService;

    private String propertyFile;

    private String protocol;

    private String propertyDisplayName;

    private Set<String> supportedStorages = new HashSet<>();

    public Set<String> getSupportedStorages() {
        return supportedStorages;
    }

    public void setSupportedStorages(Set<String> supportedStorages) {
        this.supportedStorages = supportedStorages;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getDescription() {
        return description;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public String getRelatedService() {
        return relatedService;
    }

    public void setRelatedService(String relatedService) {
        this.relatedService = relatedService;
    }

    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPropertyDisplayName() {
        return propertyDisplayName;
    }

    public void setPropertyDisplayName(String propertyDisplayName) {
        this.propertyDisplayName = propertyDisplayName;
    }

    public ConfigQueryEntry copy() {
        ConfigQueryEntry configQueryEntry = new ConfigQueryEntry();
        configQueryEntry.setProtocol(getProtocol());
        configQueryEntry.setDefaultPath(getDefaultPath());
        configQueryEntry.setPropertyFile(getPropertyFile());
        configQueryEntry.setDescription(getDescription());
        configQueryEntry.setPropertyDisplayName(getPropertyDisplayName());
        configQueryEntry.setRelatedService(getRelatedService());
        configQueryEntry.setPropertyName(getPropertyName());
        return configQueryEntry;
    }
}
