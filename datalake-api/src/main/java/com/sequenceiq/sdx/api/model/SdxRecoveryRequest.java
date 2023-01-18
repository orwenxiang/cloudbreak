package com.sequenceiq.sdx.api.model;

import java.util.StringJoiner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SdxRecoveryRequest {

    @Schema(description = ModelDescriptions.RECOVERY_TYPE)
    private SdxRecoveryType type;

    public SdxRecoveryType getType() {
        return type;
    }

    public void setType(SdxRecoveryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SdxRecoveryRequest.class.getSimpleName() + "[", "]").add("type=" + type).toString();
    }
}
