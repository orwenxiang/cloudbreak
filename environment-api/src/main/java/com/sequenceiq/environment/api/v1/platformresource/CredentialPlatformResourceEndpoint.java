package com.sequenceiq.environment.api.v1.platformresource;

import static com.sequenceiq.environment.api.doc.ModelDescriptions.CONNECTOR_NOTES;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sequenceiq.cloudbreak.jerseyclient.RetryAndMetrics;
import com.sequenceiq.common.api.type.CdpResourceType;
import com.sequenceiq.environment.api.v1.platformresource.PlatformResourceModelDescription.OpDescription;
import com.sequenceiq.environment.api.v1.platformresource.model.AccessConfigTypeQueryParam;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformAccessConfigsResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformDisksResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformEncryptionKeysResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformGatewaysResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformIpPoolsResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformNetworksResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformNoSqlTablesResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformPrivateDnsZonesResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformResourceGroupsResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformSecurityGroupsResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformSshKeysResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.PlatformVmtypesResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.RegionResponse;
import com.sequenceiq.environment.api.v1.platformresource.model.TagSpecificationsResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RetryAndMetrics
@Path("/v1/platform_resources")
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "/v1/platform_resources", description = PlatformResourceModelDescription.CONNECTOR_V1_DESCRIPTION)
public interface CredentialPlatformResourceEndpoint {

    @GET
    @Path("machine_types")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_VMTYPES_BY_CREDENTIAL, description =  CONNECTOR_NOTES,
            operationId = "getVmTypesByCredential")
    PlatformVmtypesResponse getVmTypesByCredential(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone,
            @QueryParam("resourceType") CdpResourceType resourceType);

    @GET
    @Path("regions")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_REGIONS_BY_CREDENTIAL, description =  CONNECTOR_NOTES,
            operationId = "getRegionsByCredential")
    RegionResponse getRegionsByCredential(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone,
            @DefaultValue("true") @QueryParam("availabilityZonesNeeded") boolean availabilityZonesNeeded);

    @GET
    @Path("disk_types")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_DISK_TYPES, description =  CONNECTOR_NOTES,
            operationId = "getDisktypes")
    PlatformDisksResponse getDisktypes();

    @GET
    @Path("networks")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_NETWORKS, description =  CONNECTOR_NOTES,
            operationId = "getPlatformNetworks")
    PlatformNetworksResponse getCloudNetworks(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone,
            @QueryParam("networkId") String networkId,
            @QueryParam("subnetIds") String subnetIds,
            @QueryParam("sharedProjectId") String sharedProjectId);

    @GET
    @Path("ip_pools")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_IPPOOLS, description =  CONNECTOR_NOTES,
            operationId = "getIpPoolsCredentialId")
    PlatformIpPoolsResponse getIpPoolsCredentialId(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone);

    @GET
    @Path("gateways")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_GATEWAYS, description =  CONNECTOR_NOTES,
            operationId = "getGatewaysCredentialId")
    PlatformGatewaysResponse getGatewaysCredentialId(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone);

    @GET
    @Path("encryption_keys")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_ENCRYPTIONKEYS, description =  CONNECTOR_NOTES,
            operationId = "getEncryptionKeys")
    PlatformEncryptionKeysResponse getEncryptionKeys(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone);

    @GET
    @Path("security_groups")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_SECURITYGROUPS, description =  CONNECTOR_NOTES,
            operationId = "getPlatformSecurityGroups")
    PlatformSecurityGroupsResponse getSecurityGroups(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone,
            @QueryParam("sharedProjectId") String sharedProjectId);

    @GET
    @Path("ssh_keys")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_SSHKEYS, description =  CONNECTOR_NOTES,
            operationId = "getPlatformSShKeys")
    PlatformSshKeysResponse getCloudSshKeys(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone);

    @GET
    @Path("access_configs")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_ACCESSCONFIGS, description =  CONNECTOR_NOTES,
            operationId = "getAccessConfigs")
    PlatformAccessConfigsResponse getAccessConfigs(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone,
            @QueryParam("accessConfigType") @DefaultValue("INSTANCE_PROFILE") AccessConfigTypeQueryParam accessConfigType);

    @GET
    @Path("tag_specifications")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_TAG_SPECIFICATIONS, description =  CONNECTOR_NOTES,
            operationId = "getTagSpecifications")
    TagSpecificationsResponse getTagSpecifications();

    @GET
    @Path("nosql_tables")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_NOSQL_TABLES, description =  CONNECTOR_NOTES,
            operationId = "getNoSqlTables")
    PlatformNoSqlTablesResponse getNoSqlTables(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") @NotEmpty String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone);

    @GET
    @Path("resource_groups")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_RESOURCE_GROUPS, description =  CONNECTOR_NOTES,
            operationId = "getResourceGroups")
    PlatformResourceGroupsResponse getResourceGroups(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("region") String region,
            @QueryParam("platformVariant") String platformVariant,
            @QueryParam("availabilityZone") String availabilityZone);

    @GET
    @Path("private_dns_zones")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  OpDescription.GET_PRIVATE_DNS_ZONES, description =  CONNECTOR_NOTES,
            operationId = "getPrivateDnsZones")
    PlatformPrivateDnsZonesResponse getPrivateDnsZones(
            @QueryParam("credentialName") String credentialName,
            @QueryParam("credentialCrn") String credentialCrn,
            @QueryParam("platformVariant") String platformVariant);

}
