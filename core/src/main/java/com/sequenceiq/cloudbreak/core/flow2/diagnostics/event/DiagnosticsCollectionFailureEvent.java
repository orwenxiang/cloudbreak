package com.sequenceiq.cloudbreak.core.flow2.diagnostics.event;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;
import static com.sequenceiq.cloudbreak.core.flow2.diagnostics.event.DiagnosticsCollectionStateSelectors.FAILED_DIAGNOSTICS_COLLECTION_EVENT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sequenceiq.cloudbreak.common.event.Selectable;
import com.sequenceiq.common.model.diagnostics.DiagnosticParameters;

public class DiagnosticsCollectionFailureEvent extends DiagnosticsCollectionEvent implements Selectable {

    @JsonTypeInfo(use = CLASS, property = "@type")
    private final Exception exception;

    private final String failureType;

    @JsonCreator
    public DiagnosticsCollectionFailureEvent(
            @JsonProperty("resourceId") Long resourceId,
            @JsonProperty("exception") Exception exception,
            @JsonProperty("resourceCrn") String resourceCrn,
            @JsonProperty("parameters") DiagnosticParameters parameters,
            @JsonProperty("failureType") String failureType) {
        super(FAILED_DIAGNOSTICS_COLLECTION_EVENT.name(), resourceId, resourceCrn, parameters, null, null, null);
        this.exception = exception;
        this.failureType = failureType;
    }

    @Override
    public String selector() {
        return FAILED_DIAGNOSTICS_COLLECTION_EVENT.name();
    }

    public Exception getException() {
        return exception;
    }

    public String getFailureType() {
        return failureType;
    }

    @Override
    public String toString() {
        return "DiagnosticsCollectionFailureEvent{" +
                "exception=" + exception +
                ", failureType='" + failureType + '\'' +
                "} " + super.toString();
    }
}

