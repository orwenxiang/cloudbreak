package com.sequenceiq.periscope.converter;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.periscope.api.model.TimeAlertResponse;
import com.sequenceiq.periscope.domain.TimeAlert;

@Component
public class TimeAlertResponseConverter extends AbstractConverter<TimeAlertResponse, TimeAlert> {

    @Inject
    private ScalingPolicyResponseConverter scalingPolicyResponseConverter;

    @Override
    public TimeAlertResponse convert(TimeAlert source) {
        TimeAlertResponse json = new TimeAlertResponse();
        json.setCrn(source.getAlertCrn());
        json.setAlertName(source.getName());
        json.setCron(source.getCron());
        json.setTimeZone(source.getTimeZone());
        json.setDescription(source.getDescription());
        if (source.getScalingPolicy() != null) {
            json.setScalingPolicy(scalingPolicyResponseConverter.convert(source.getScalingPolicy()));
        }
        return json;
    }

}
