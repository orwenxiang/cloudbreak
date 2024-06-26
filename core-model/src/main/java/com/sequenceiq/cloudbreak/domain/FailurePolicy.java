package com.sequenceiq.cloudbreak.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import com.sequenceiq.cloudbreak.converter.AdjustmentTypeConverter;
import com.sequenceiq.common.api.type.AdjustmentType;

@Entity
public class FailurePolicy implements ProvisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "failurepolicy_generator")
    @SequenceGenerator(name = "failurepolicy_generator", sequenceName = "failurepolicy_id_seq", allocationSize = 1)
    private Long id;

    private Long threshold;

    @Convert(converter = AdjustmentTypeConverter.class)
    private AdjustmentType adjustmentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }
}
