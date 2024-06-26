package com.sequenceiq.freeipa.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import com.sequenceiq.freeipa.api.v1.freeipa.user.model.FailureDetails;
import com.sequenceiq.freeipa.api.v1.freeipa.user.model.SuccessDetails;
import com.sequenceiq.freeipa.api.v1.operation.model.OperationState;
import com.sequenceiq.freeipa.api.v1.operation.model.OperationType;
import com.sequenceiq.freeipa.entity.util.ListFailureDetailsToString;
import com.sequenceiq.freeipa.entity.util.ListStringToString;
import com.sequenceiq.freeipa.entity.util.ListSuccessDetailsToString;
import com.sequenceiq.freeipa.entity.util.OperationStateConverter;
import com.sequenceiq.freeipa.entity.util.OperationTypeConverter;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "syncoperation_generator")
    @SequenceGenerator(name = "syncoperation_generator", sequenceName = "syncoperation_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String operationId;

    @Column(nullable = false)
    private String accountId;

    @Convert(converter = OperationTypeConverter.class)
    @Column(nullable = false)
    private OperationType operationType;

    @Convert(converter = OperationStateConverter.class)
    @Column(nullable = false)
    private OperationState status;

    @Convert(converter = ListStringToString.class)
    @Column(columnDefinition = "TEXT")
    private List<String> environmentList = List.of();

    @Convert(converter = ListStringToString.class)
    @Column(columnDefinition = "TEXT")
    private List<String> userList = List.of();

    @Convert(converter = ListSuccessDetailsToString.class)
    @Column(columnDefinition = "TEXT")
    private List<SuccessDetails> successList = List.of();

    @Convert(converter = ListFailureDetailsToString.class)
    @Column(columnDefinition = "TEXT")
    private List<FailureDetails> failureList = List.of();

    @Column(columnDefinition = "TEXT")
    private String error;

    @Column(nullable = false)
    private Long startTime;

    private Long endTime;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        startTime = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public OperationState getStatus() {
        return status;
    }

    public void setStatus(OperationState status) {
        this.status = status;
    }

    public List<String> getEnvironmentList() {
        return nullToEmpty(environmentList);
    }

    public void setEnvironmentList(List<String> environmentList) {
        this.environmentList = nullToEmpty(environmentList);
    }

    public List<String> getUserList() {
        return nullToEmpty(userList);
    }

    public void setUserList(List<String> userList) {
        this.userList = nullToEmpty(userList);
    }

    public List<SuccessDetails> getSuccessList() {
        return nullToEmpty(successList);
    }

    public void setSuccessList(List<SuccessDetails> successList) {
        this.successList = nullToEmpty(successList);
    }

    public List<FailureDetails> getFailureList() {
        return nullToEmpty(failureList);
    }

    public void setFailureList(List<FailureDetails> failureList) {
        this.failureList = nullToEmpty(failureList);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private <U> List<U> nullToEmpty(List<U> list) {
        if (list == null) {
            return List.of();
        }
        return list;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", operationId='" + operationId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", operationType=" + operationType +
                ", status=" + status +
                ", environmentList=" + environmentList +
                ", userList=" + userList +
                ", successList=" + successList +
                ", failureList=" + failureList +
                ", error='" + error + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", version=" + version +
                '}';
    }
}
