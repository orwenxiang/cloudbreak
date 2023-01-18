package com.sequenceiq.common.api.backup.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.common.api.backup.base.BackupBase;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name ="BackupRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackupRequest extends BackupBase {
    @Override
    public String toString() {
        return super.toString() + ", " + "BackupRequest{}";
    }
}
