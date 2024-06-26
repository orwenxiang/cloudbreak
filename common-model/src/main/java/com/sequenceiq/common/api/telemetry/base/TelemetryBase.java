package com.sequenceiq.common.api.telemetry.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.sequenceiq.common.api.telemetry.doc.TelemetryModelDescription;

import io.swagger.v3.oas.annotations.media.Schema;

public abstract class TelemetryBase implements Serializable {

    @Schema(description = TelemetryModelDescription.TELEMETRY_FLUENT_ATTRIBUTES)
    private Map<String, Object> fluentAttributes = new HashMap<>();

    public Map<String, Object> getFluentAttributes() {
        return fluentAttributes;
    }

    public void setFluentAttributes(Map<String, Object> fluentAttributes) {
        this.fluentAttributes = fluentAttributes;
    }

    @Override
    public String toString() {
        return "TelemetryBase{" +
                "fluentAttributes=" + fluentAttributes +
                '}';
    }
}
