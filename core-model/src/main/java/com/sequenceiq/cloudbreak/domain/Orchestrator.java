package com.sequenceiq.cloudbreak.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import com.sequenceiq.cloudbreak.common.json.Json;
import com.sequenceiq.cloudbreak.common.json.JsonToString;

@Entity
public class Orchestrator implements ProvisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orchestrator_generator")
    @SequenceGenerator(name = "orchestrator_generator", sequenceName = "orchestrator_id_seq", allocationSize = 1)
    private Long id;

    private String apiEndpoint;

    @Column(nullable = false)
    private String type;

    @Convert(converter = JsonToString.class)
    @Column(columnDefinition = "TEXT")
    private Json attributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Json getAttributes() {
        return attributes;
    }

    public void setAttributes(Json attributes) {
        this.attributes = attributes;
    }
}
