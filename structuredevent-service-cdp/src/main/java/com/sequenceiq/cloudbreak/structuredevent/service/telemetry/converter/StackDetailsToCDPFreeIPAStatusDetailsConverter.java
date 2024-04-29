package com.sequenceiq.cloudbreak.structuredevent.service.telemetry.converter;

import static com.cloudera.thunderhead.service.common.usage.UsageProto.CDPFreeIPAStatusDetails;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.structuredevent.event.StackDetails;

@Component
public class StackDetailsToCDPFreeIPAStatusDetailsConverter {

    private static final int MAX_STRING_LENGTH = 1500;

    public CDPFreeIPAStatusDetails convert(StackDetails stackDetails) {
        CDPFreeIPAStatusDetails.Builder cdpFreeIpaStatusDetails = CDPFreeIPAStatusDetails.newBuilder();
        if (stackDetails != null) {
            cdpFreeIpaStatusDetails.setStackStatus(defaultIfEmpty(stackDetails.getStatus(), ""));
            cdpFreeIpaStatusDetails.setStackDetailedStatus(defaultIfEmpty(stackDetails.getDetailedStatus(), ""));
            cdpFreeIpaStatusDetails.setStackStatusReason(defaultIfEmpty(StringUtils.substring(stackDetails.getStatusReason(),
                    0, Math.min(StringUtils.length(stackDetails.getStatusReason()), MAX_STRING_LENGTH)), ""));
        }

        return cdpFreeIpaStatusDetails.build();
    }
}
