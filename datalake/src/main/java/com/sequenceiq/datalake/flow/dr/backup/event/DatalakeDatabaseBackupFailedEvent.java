package com.sequenceiq.datalake.flow.dr.backup.event;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sequenceiq.datalake.flow.SdxEvent;

public class DatalakeDatabaseBackupFailedEvent extends SdxEvent {

    @JsonTypeInfo(use = CLASS, property = "@type")
    private final Exception exception;

    @JsonCreator
    public DatalakeDatabaseBackupFailedEvent(
            @JsonProperty("resourceId") Long sdxId,
            @JsonProperty("userId") String userId,
            @JsonProperty("exception") Exception exception) {
        super(sdxId, userId);
        this.exception = exception;
    }

    public static DatalakeDatabaseBackupFailedEvent from(SdxEvent event, Exception exception) {
        return new DatalakeDatabaseBackupFailedEvent(event.getResourceId(), event.getUserId(), exception);
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "DatalakeDatabaseBackupFailedEvent{" +
                "exception= " + exception.toString() +
                '}';
    }
}
