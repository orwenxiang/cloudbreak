package com.sequenceiq.mock.freeipa.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.cloud.model.CloudVmMetaDataStatus;

@Component
public class DnsZoneDelResponse extends AbstractFreeIpaResponse<Object> {
    @Override
    public String method() {
        return "dnszone_del";
    }

    @Override
    protected Object handleInternal(List<CloudVmMetaDataStatus> metadatas, String body) {
        return "";
    }
}
