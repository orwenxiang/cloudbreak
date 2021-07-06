package com.sequenceiq.environment.environment.validation.securitygroup.azure;

import static com.sequenceiq.cloudbreak.common.mappable.CloudPlatform.AZURE;
import static com.sequenceiq.cloudbreak.util.SecurityGroupSeparator.getSecurityGroupIds;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.sequenceiq.cloudbreak.cloud.model.CloudSecurityGroup;
import com.sequenceiq.cloudbreak.cloud.model.CloudSecurityGroups;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.validation.ValidationResult;
import com.sequenceiq.environment.environment.domain.Region;
import com.sequenceiq.environment.environment.dto.EnvironmentDto;
import com.sequenceiq.environment.environment.dto.EnvironmentValidationDto;
import com.sequenceiq.environment.environment.dto.SecurityAccessDto;
import com.sequenceiq.environment.environment.validation.securitygroup.EnvironmentSecurityGroupValidator;
import com.sequenceiq.environment.platformresource.PlatformParameterService;
import com.sequenceiq.environment.platformresource.PlatformResourceRequest;

@Component
public class AzureEnvironmentSecurityGroupValidator implements EnvironmentSecurityGroupValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureEnvironmentSecurityGroupValidator.class);

    private PlatformParameterService platformParameterService;

    public AzureEnvironmentSecurityGroupValidator(PlatformParameterService platformParameterService) {
        this.platformParameterService = platformParameterService;
    }

    @Override
    public void validate(EnvironmentValidationDto environmentValidationDto, ValidationResult.ValidationResultBuilder resultBuilder) {
        EnvironmentDto environmentDto = environmentValidationDto.getEnvironmentDto();
        SecurityAccessDto securityAccessDto = environmentDto.getSecurityAccess();
        if (securityAccessDto != null) {
            if (onlyOneSecurityGroupIdDefined(securityAccessDto)) {
                resultBuilder.error(securityGroupIdsMustBePresent());
            } else if (isSecurityGroupIdDefined(securityAccessDto)) {
                if (!Strings.isNullOrEmpty(securityAccessDto.getDefaultSecurityGroupId())) {
                    validateSecurityGroup(environmentDto, resultBuilder, securityAccessDto.getDefaultSecurityGroupId());
                }
                if (!Strings.isNullOrEmpty(securityAccessDto.getSecurityGroupIdForKnox())) {
                    validateSecurityGroup(environmentDto, resultBuilder, securityAccessDto.getSecurityGroupIdForKnox());
                }
            }
        }
    }

    private void validateSecurityGroup(EnvironmentDto environmentDto, ValidationResult.ValidationResultBuilder resultBuilder, String securityGroupIds) {
        Region region = environmentDto.getRegions().iterator().next();
        PlatformResourceRequest request = platformParameterService.getPlatformResourceRequest(
                environmentDto.getAccountId(),
                environmentDto.getCredential().getName(),
                null,
                region.getName(),
                getCloudPlatform().name(),
                null);

        CloudSecurityGroups securityGroups = platformParameterService.getSecurityGroups(request);

        boolean securityGroupFoundInRegion = false;
        Map<String, Set<CloudSecurityGroup>> cloudSecurityGroupsResponses = securityGroups.getCloudSecurityGroupsResponses();
        if (Objects.nonNull(cloudSecurityGroupsResponses)) {
            Set<CloudSecurityGroup> cloudSecurityGroups = cloudSecurityGroupsResponses.get(region.getName());
            for (String securityGroupId : getSecurityGroupIds(securityGroupIds)) {
                securityGroupFoundInRegion = false;
                if (Objects.nonNull(cloudSecurityGroups)) {
                    for (CloudSecurityGroup cloudSecurityGroup : cloudSecurityGroups) {
                        String groupId = cloudSecurityGroup.getGroupId();
                        if (!Strings.isNullOrEmpty(groupId) && groupId.equalsIgnoreCase(securityGroupId)) {
                            securityGroupFoundInRegion = true;
                            break;
                        }
                    }
                }
                if (!securityGroupFoundInRegion) {
                    break;
                }
            }
        }
        if (!securityGroupFoundInRegion) {
            LOGGER.info("The security groups {} are not presented in the region {}", securityGroupIds, region.getName());
            resultBuilder.error(securityGroupNotInTheSameRegion(securityGroupIds, region.getName()));
        }
    }

    @Override
    public CloudPlatform getCloudPlatform() {
        return AZURE;
    }

}
