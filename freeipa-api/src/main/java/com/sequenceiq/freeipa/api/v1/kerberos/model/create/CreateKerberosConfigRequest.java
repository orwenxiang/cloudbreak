package com.sequenceiq.freeipa.api.v1.kerberos.model.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.auth.crn.CrnResourceDescriptor;
import com.sequenceiq.cloudbreak.validation.ValidCrn;
import com.sequenceiq.freeipa.api.v1.kerberos.doc.KerberosConfigModelDescription;
import com.sequenceiq.freeipa.api.v1.kerberos.validation.ValidKerberosRequest;
import com.sequenceiq.service.api.doc.ModelDescriptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateKerberosConfigV1Request")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@ValidKerberosRequest
public class CreateKerberosConfigRequest {
    @Valid
    @Schema
    private ActiveDirectoryKerberosDescriptor activeDirectory;

    @Valid
    @Schema
    private FreeIpaKerberosDescriptor freeIpa;

    @Valid
    @Schema
    private MITKerberosDescriptor mit;

    @Schema(description = KerberosConfigModelDescription.KERBEROS_CONFIG_NAME, required = true)
    @NotNull
    @NotEmpty
    private String name;

    @Size(max = 1000)
    @Schema(description = ModelDescriptions.DESCRIPTION)
    private String description;

    @ValidCrn(resource = CrnResourceDescriptor.ENVIRONMENT)
    @NotNull
    @Schema(description = ModelDescriptions.ENVIRONMENT_CRN, required = true)
    private String environmentCrn;

    public ActiveDirectoryKerberosDescriptor getActiveDirectory() {
        return activeDirectory;
    }

    public void setActiveDirectory(ActiveDirectoryKerberosDescriptor activeDirectoryKerberosDescriptor) {
        activeDirectory = activeDirectoryKerberosDescriptor;
    }

    public FreeIpaKerberosDescriptor getFreeIpa() {
        return freeIpa;
    }

    public void setFreeIpa(FreeIpaKerberosDescriptor freeIpaKerberosDescriptor) {
        freeIpa = freeIpaKerberosDescriptor;
    }

    public MITKerberosDescriptor getMit() {
        return mit;
    }

    public void setMit(MITKerberosDescriptor mitKerberosDescriptor) {
        mit = mitKerberosDescriptor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironmentCrn() {
        return environmentCrn;
    }

    public void setEnvironmentCrn(String environmentCrn) {
        this.environmentCrn = environmentCrn;
    }
}
