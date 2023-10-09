package com.sequenceiq.cloudbreak.converter;

import static com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.ClusterStatus;
import static com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.ServiceType.Value.DATAHUB;
import static com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.Sync;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceStatus.SERVICES_HEALTHY;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceStatus.SERVICES_RUNNING;
import static com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceStatus.SERVICES_UNHEALTHY;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.InstanceResource;
import com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.MeteringEvent;
import com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.Resource;
import com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.ServiceType;
import com.cloudera.thunderhead.service.meteringv2.events.MeteringV2EventsProto.StatusChange;
import com.sequenceiq.cloudbreak.auth.crn.Crn;
import com.sequenceiq.cloudbreak.cloud.model.StackTags;
import com.sequenceiq.cloudbreak.dto.InstanceGroupDto;
import com.sequenceiq.cloudbreak.dto.StackDtoDelegate;
import com.sequenceiq.cloudbreak.tag.ClusterTemplateApplicationTag;
import com.sequenceiq.cloudbreak.view.InstanceMetadataView;

@Component
public class StackDtoToMeteringEventConverter {

    public static final String CLOUDERA_EXTERNAL_RESOURCE_NAME_TAG = "Cloudera-External-Resource-Name";

    private static final Logger LOGGER = LoggerFactory.getLogger(StackDtoToMeteringEventConverter.class);

    private static final int DEFAULT_VERSION = 1;

    public MeteringEvent convertToSyncEvent(StackDtoDelegate stack) {
        MeteringEvent.Builder builder = convertBase(stack);
        return builder.setSync(convertSync(stack)).build();
    }

    public MeteringEvent convertToStatusChangeEvent(StackDtoDelegate stack, ClusterStatus.Value eventOperation) {
        MeteringEvent.Builder builder = convertBase(stack);
        return builder.setStatusChange(convertStatusChange(stack, eventOperation)).build();
    }

    private Sync convertSync(StackDtoDelegate stack) {
        return Sync.newBuilder()
                .addAllResources(convertResources(stack))
                .build();
    }

    private List<Resource> convertResources(StackDtoDelegate stack) {
        return stack.getInstanceGroupDtos().stream().flatMap(this::convertInstanceGroup).toList();
    }

    private Stream<Resource> convertInstanceGroup(InstanceGroupDto instanceGroup) {
        return instanceGroup.getNotDeletedInstanceMetaData().stream()
                .filter(instanceMetadata -> Set.of(SERVICES_RUNNING, SERVICES_HEALTHY, SERVICES_UNHEALTHY).contains(instanceMetadata.getInstanceStatus()))
                .map(instanceMetadata -> convertInstance(instanceMetadata, instanceGroup.getInstanceGroup().getTemplate().getInstanceType()));
    }

    private Resource convertInstance(InstanceMetadataView instanceMetadata, String instanceType) {
        return Resource.newBuilder()
                .setId(instanceMetadata.getInstanceId())
                .setInstanceResource(InstanceResource.newBuilder()
                        .setIpAddress(StringUtils.isNotEmpty(instanceMetadata.getPublicIp()) ? instanceMetadata.getPublicIp() : instanceMetadata.getPrivateIp())
                        .setInstanceType(instanceType)
                        .build())
                .build();
    }

    private StatusChange convertStatusChange(StackDtoDelegate stack, ClusterStatus.Value eventOperation) {
        return StatusChange.newBuilder()
                .setStatus(eventOperation)
                .addAllResources(convertResources(stack))
                .build();
    }

    private MeteringEvent.Builder convertBase(StackDtoDelegate stack) {
        Map<String, String> applicationTags = getApplicationTags(stack);
        return MeteringEvent.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setTimestamp(System.currentTimeMillis())
                .setVersion(DEFAULT_VERSION)
                .setServiceType(getServiceType(applicationTags, DATAHUB))
                .setServiceFeature(getServiceFeature(applicationTags))
                .setResourceCrn(getResourceCrn(stack, applicationTags))
                .setEnvironmentCrn(stack.getEnvironmentCrn());
    }

    private ServiceType.Value getServiceType(Map<String, String> applicationTags, ServiceType.Value defaultServiceType) {
        String serviceTypeName = applicationTags.get(ClusterTemplateApplicationTag.SERVICE_TYPE.key());
        return EnumUtils.getEnum(ServiceType.Value.class, serviceTypeName, defaultServiceType);
    }

    private String getServiceFeature(Map<String, String> applicationTags) {
        String serviceFeature = applicationTags.get(ClusterTemplateApplicationTag.SERVICE_FEATURE.key());
        return serviceFeature != null ? serviceFeature : StringUtils.EMPTY;
    }

    private String getResourceCrn(StackDtoDelegate stack, Map<String, String> applicationTags) {
        String externalCrn = applicationTags.get(CLOUDERA_EXTERNAL_RESOURCE_NAME_TAG);
        if (StringUtils.isNotEmpty(externalCrn) && Crn.isCrn(externalCrn)) {
            return externalCrn;
        } else {
            return stack.getResourceCrn();
        }
    }

    private Map<String, String> getApplicationTags(StackDtoDelegate stack) {
        if (stack.getTags() != null) {
            try {
                StackTags stackTags = stack.getTags().get(StackTags.class);
                if (stackTags != null) {
                    return stackTags.getApplicationTags();
                }
            } catch (IOException e) {
                LOGGER.warn("Stack related application tags cannot be parsed.", e);
            }
        }
        return new HashMap<>();
    }
}
