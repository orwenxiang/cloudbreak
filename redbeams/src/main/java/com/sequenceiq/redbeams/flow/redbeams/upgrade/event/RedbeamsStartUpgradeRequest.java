package com.sequenceiq.redbeams.flow.redbeams.upgrade.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sequenceiq.cloudbreak.common.database.TargetMajorVersion;
import com.sequenceiq.redbeams.flow.redbeams.common.RedbeamsEvent;
import com.sequenceiq.redbeams.flow.redbeams.upgrade.RedbeamsUpgradeEvent;

public class RedbeamsStartUpgradeRequest extends RedbeamsEvent {

    private final TargetMajorVersion targetMajorVersion;

    @JsonCreator
    public RedbeamsStartUpgradeRequest(
            @JsonProperty("resourceId") Long resourceId,
            @JsonProperty("targetMajorVersion") TargetMajorVersion targetMajorVersion) {

        super(RedbeamsUpgradeEvent.REDBEAMS_START_UPGRADE_EVENT.selector(), resourceId);
        this.targetMajorVersion = targetMajorVersion;
    }

    public TargetMajorVersion getTargetMajorVersion() {
        return targetMajorVersion;
    }

    @Override
    public String toString() {
        return "RedbeamsStartUpgradeRequest{" +
                "targetMajorVersion=" + targetMajorVersion +
                "} " + super.toString();
    }

}
