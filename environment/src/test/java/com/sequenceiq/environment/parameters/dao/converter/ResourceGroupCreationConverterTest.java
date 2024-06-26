package com.sequenceiq.environment.parameters.dao.converter;

import jakarta.persistence.AttributeConverter;

import com.sequenceiq.cloudbreak.converter.DefaultEnumConverterBaseTest;
import com.sequenceiq.environment.parameter.dto.ResourceGroupCreation;

public class ResourceGroupCreationConverterTest extends DefaultEnumConverterBaseTest<ResourceGroupCreation> {

    @Override
    public ResourceGroupCreation getDefaultValue() {
        return ResourceGroupCreation.USE_EXISTING;
    }

    @Override
    public AttributeConverter<ResourceGroupCreation, String> getVictim() {
        return new ResourceGroupCreationConverter();
    }
}
