package com.sequenceiq.freeipa.api.v1.dns;

import static com.sequenceiq.freeipa.api.v1.dns.model.DnsRecordRegexpPatterns.DNS_CNAME_MSG;
import static com.sequenceiq.freeipa.api.v1.dns.model.DnsRecordRegexpPatterns.DNS_CNAME_PATTERN;
import static com.sequenceiq.freeipa.api.v1.dns.model.DnsRecordRegexpPatterns.DNS_HOSTNAME_MSG;
import static com.sequenceiq.freeipa.api.v1.dns.model.DnsRecordRegexpPatterns.DNS_HOSTNAME_PATTERN;
import static com.sequenceiq.freeipa.api.v1.dns.model.DnsRecordRegexpPatterns.DNS_ZONE_MSG;
import static com.sequenceiq.freeipa.api.v1.dns.model.DnsRecordRegexpPatterns.DNS_ZONE_PATTERN;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sequenceiq.cloudbreak.auth.security.internal.AccountId;
import com.sequenceiq.cloudbreak.jerseyclient.RetryAndMetrics;
import com.sequenceiq.freeipa.api.v1.dns.doc.DnsOperationDescriptions;
import com.sequenceiq.freeipa.api.v1.dns.model.AddDnsARecordRequest;
import com.sequenceiq.freeipa.api.v1.dns.model.AddDnsCnameRecordRequest;
import com.sequenceiq.freeipa.api.v1.dns.model.AddDnsZoneForSubnetIdsRequest;
import com.sequenceiq.freeipa.api.v1.dns.model.AddDnsZoneForSubnetsRequest;
import com.sequenceiq.freeipa.api.v1.dns.model.AddDnsZoneForSubnetsResponse;
import com.sequenceiq.freeipa.api.v1.freeipa.stack.doc.FreeIpaNotes;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Path("/v1/dns")
@RetryAndMetrics
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "/v1/dns", description = "Amange DNS in FreeIPA")
public interface DnsV1Endpoint {
    @POST
    @Path("zone/cidr")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.ADD_DNS_ZONE_FOR_SUBNETS, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "addDnsZoneForSubnetsV1")
    AddDnsZoneForSubnetsResponse addDnsZoneForSubnets(@Valid AddDnsZoneForSubnetsRequest request);

    @POST
    @Path("zone/id")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.ADD_DNS_ZONE_FOR_SUBNET_IDS, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "addDnsZoneForSubnetIdsV1")
    AddDnsZoneForSubnetsResponse addDnsZoneForSubnetIds(@Valid AddDnsZoneForSubnetIdsRequest request);

    @GET
    @Path("zone")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.LIST_DNS_ZONES, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "listDnsZonesV1")
    Set<String> listDnsZones(@QueryParam("environment") @NotEmpty String environmentCrn);

    @DELETE
    @Path("zone/cidr")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.DELETE_DNS_ZONE_BY_SUBNET, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "deleteDnsZoneBySubnetV1")
    void deleteDnsZoneBySubnet(@QueryParam("environment") @NotEmpty String environmentCrn,
            @QueryParam("subnet") @NotEmpty
            @Pattern(regexp = "(^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\/([0-9]|[1-2][0-9]|3[0-2]))$)",
                    message = "Must be in valid CIDR format eg. 192.168.1.0/24") String subnet);

    @DELETE
    @Path("zone/id")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.DELETE_DNS_ZONE_BY_SUBNET_ID, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "deleteDnsZoneBySubnetIdV1")
    void deleteDnsZoneBySubnetId(@QueryParam("environment") @NotEmpty String environmentCrn, @QueryParam("networkId") @NotEmpty String networkId,
            @QueryParam("subnetId") @NotEmpty String subnetId);

    @DELETE
    @Path("record")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.DELETE_DNS_RECORD_BY_FQDN, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "deleteDnsRecordByFqdnV1")
    void deleteDnsRecordsByFqdn(@QueryParam("environment") @NotEmpty String environmentCrn, @QueryParam("fqdn") @NotEmpty List<String> fqdns);

    @POST
    @Path("record/a")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.ADD_DNS_A_RECORD, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "addDnsARecordV1")
    void addDnsARecord(@Valid @NotNull AddDnsARecordRequest request);

    @POST
    @Path("record/a/internal")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.ADD_DNS_A_RECORD, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "addDnsARecordV1Internal")
    void addDnsARecordInternal(@AccountId @QueryParam("accountId") @NotEmpty String accountId, @Valid @NotNull AddDnsARecordRequest request);

    @DELETE
    @Path("record/a")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.DELETE_DNS_A_RECORD, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "deleteDnsARecordV1")
    void deleteDnsARecord(@QueryParam("environment") @NotEmpty String environmentCrn,
            @QueryParam("dnsZone") @Pattern(regexp = DNS_ZONE_PATTERN, message = DNS_ZONE_MSG) String dnsZone,
            @QueryParam("hostname") @NotEmpty @Pattern(regexp = DNS_HOSTNAME_PATTERN, message = DNS_HOSTNAME_MSG) String hostname);

    @POST
    @Path("record/cname")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.ADD_DNS_CNAME_RECORD, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "addDnsCnameRecordV1")
    void addDnsCnameRecord(@Valid @NotNull AddDnsCnameRecordRequest request);

    @POST
    @Path("record/cname/internal")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.ADD_DNS_CNAME_RECORD, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "addDnsCnameRecordV1Internal")
    void addDnsCnameRecordInternal(@AccountId @QueryParam("accountId") @NotEmpty String accountId, @Valid @NotNull AddDnsCnameRecordRequest request);

    @DELETE
    @Path("record/cname")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary =  DnsOperationDescriptions.DELETE_DNS_CNAME_RECORD, description =  FreeIpaNotes.FREEIPA_NOTES,
            operationId = "deleteDnsCnameRecordV1")
    void deleteDnsCnameRecord(@QueryParam("environment") @NotEmpty String environmentCrn,
            @QueryParam("dnsZone") @Pattern(regexp = DNS_ZONE_PATTERN, message = DNS_ZONE_MSG) String dnsZone,
            @QueryParam("cname") @NotEmpty @Pattern(regexp = DNS_CNAME_PATTERN, message = DNS_CNAME_MSG) String cname);
}
