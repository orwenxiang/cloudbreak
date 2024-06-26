package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;
import com.sequenceiq.common.model.JsonEntity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AwsInstanceTemplateV4SpotParameters implements JsonEntity {

    @Schema(description = TemplateModelDescription.SPOT_PERCENTAGE)
    @Min(value = 0, message = "Spot percentage must be between 0 and 100 percent")
    @Max(value = 100, message = "Spot percentage must be between 0 and 100 percent")
    @Digits(fraction = 0, integer = 3, message = "Spot percentage has to be a number")
    private int percentage;

    @Schema(description = TemplateModelDescription.SPOT_MAX_PRICE)
    @DecimalMin(value = "0.001", message = "Spot max price must be between 0.001 and 255 with maximum 4 fraction digits")
    @Max(value = 255, message = "Spot max price must be between 0.001 and 255 with maximum 4 fraction digits")
    @Digits(fraction = 4, integer = 3, message = "Spot max price must be between 0.001 and 255 with maximum 4 fraction digits")
    private Double maxPrice;

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
