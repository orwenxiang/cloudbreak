package com.sequenceiq.it.cloudbreak.dto.audit;

import jakarta.ws.rs.core.Response;

import com.sequenceiq.cloudbreak.api.endpoint.v4.audits.responses.AuditEventV4Response;
import com.sequenceiq.it.cloudbreak.Prototype;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.AbstractCloudbreakTestDto;

@Prototype
public class AuditTestDto extends AbstractCloudbreakTestDto<Object, AuditEventV4Response, AuditTestDto> {

    private jakarta.ws.rs.core.Response zipResponse;

    private String resourceType;

    private Long resourceId;

    private Long auditId;

    public AuditTestDto(TestContext testContext) {
        super(null, testContext);
    }

    public AuditTestDto valid() {
        return this;
    }

    public AuditTestDto withResourceType(String resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public AuditTestDto withResourceIdByKey(String key) {
        this.resourceId = getTestContext().getSelected(key);
        return this;
    }

    public AuditTestDto withAuditId(Long auditId) {
        this.auditId = auditId;
        return this;
    }

    public String getResourceType() {
        return resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public Response getZipResponse() {
        return zipResponse;
    }

    public void setZipResponse(Response zipResponse) {
        this.zipResponse = zipResponse;
    }
}
