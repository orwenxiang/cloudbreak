package com.sequenceiq.cloudbreak.cloud.yarn.client.model.core;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YarnComponent implements Serializable {

    private String name;

    private List<Dependency> dependencies;

    private int numberOfContainers;

    private Artifact artifact;

    private String launchCommand;

    private Resource resource;

    private Boolean runPrivilegedContainer;

    private Configuration configuration;

    @JsonProperty("run_privileged_container")
    public Boolean getRunPrivilegedContainer() {
        return runPrivilegedContainer;
    }

    public void setRunPrivilegedContainer(Boolean runPrivilegedContainer) {
        this.runPrivilegedContainer = runPrivilegedContainer;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    @JsonProperty("launch_command")
    public String getLaunchCommand() {
        return launchCommand;
    }

    public void setLaunchCommand(String launchCommand) {
        this.launchCommand = launchCommand;
    }

    @JsonProperty("number_of_containers")
    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "YarnComponent {"
                + "name=" + name
                + ", dependencies=" + dependencies
                + ", numberOfContainers=" + numberOfContainers
                + ", artifact=" + artifact
                + ", launchCommand=" + launchCommand
                + ", resource=" + resource
                + ", runPrivilegedContainer=" + runPrivilegedContainer
                + ", configuration=" + configuration
                + '}';
    }
}
