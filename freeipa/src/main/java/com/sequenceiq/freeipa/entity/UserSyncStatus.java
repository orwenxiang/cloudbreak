package com.sequenceiq.freeipa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

import com.sequenceiq.cloudbreak.common.json.Json;
import com.sequenceiq.cloudbreak.common.json.JsonToString;

@Entity
public class UserSyncStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "usersyncstatus_generator")
    @SequenceGenerator(name = "usersyncstatus_generator", sequenceName = "usersyncstatus_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "stack_id", unique = true, nullable = false)
    private Long stackId;

    @Convert(converter = JsonToString.class)
    @Column(columnDefinition = "TEXT")
    private Json umsEventGenerationIds;

    @OneToOne
    private Operation lastStartedFullSync;

    @OneToOne
    private Operation lastSuccessfulFullSync;

    public UserSyncStatus() {
    }

    public UserSyncStatus(Long stackId) {
        this.stackId = stackId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStackId() {
        return stackId;
    }

    public void setStackId(Long stackId) {
        this.stackId = stackId;
    }

    public Json getUmsEventGenerationIds() {
        return umsEventGenerationIds;
    }

    public void setUmsEventGenerationIds(Json umsEventGenerationIds) {
        this.umsEventGenerationIds = umsEventGenerationIds;
    }

    public Operation getLastStartedFullSync() {
        return lastStartedFullSync;
    }

    public void setLastStartedFullSync(Operation lastStartedFullSync) {
        this.lastStartedFullSync = lastStartedFullSync;
    }

    public Operation getLastSuccessfulFullSync() {
        return lastSuccessfulFullSync;
    }

    public void setLastSuccessfulFullSync(Operation lastSuccessfulFullSync) {
        this.lastSuccessfulFullSync = lastSuccessfulFullSync;
    }
}
