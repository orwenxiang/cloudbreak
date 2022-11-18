package com.sequenceiq.cloudbreak.datalakedr.converter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.BackupDatalakeResponse;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.BackupDatalakeStatusResponse;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.BackupRestoreOperationStatus;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.HbaseBackupRestoreState;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.InternalBackupRestoreState;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.RestoreDatalakeResponse;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.RestoreDatalakeStatusResponse;
import com.cloudera.thunderhead.service.datalakedr.datalakeDRProto.SolrBackupRestoreState;
import com.sequenceiq.cloudbreak.datalakedr.model.DatalakeBackupStatusResponse;
import com.sequenceiq.cloudbreak.datalakedr.model.DatalakeBackupStatusResponse.State;
import com.sequenceiq.cloudbreak.datalakedr.model.DatalakeRestoreStatusResponse;

@Component
public class GrpcStatusResponseToDatalakeBackupRestoreStatusResponseConverter {

    static final String FAILED_STATE = "FAILED";

    static final String VALIDATION_FAILED = "VALIDATION_FAILED";

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcStatusResponseToDatalakeBackupRestoreStatusResponseConverter.class);

    public DatalakeBackupStatusResponse convert(BackupDatalakeResponse response) {
        return new DatalakeBackupStatusResponse(response.getBackupId(),
                State.valueOf(response.getOverallState()),
                Optional.ofNullable(parseFailuresFromOperationsStates(response.getOperationStates(), response.getFailureReason()))
        );
    }

    public DatalakeRestoreStatusResponse convert(RestoreDatalakeResponse response) {
        return new DatalakeRestoreStatusResponse(response.getBackupId(), response.getRestoreId(),
                State.valueOf(response.getOverallState()),
                Optional.ofNullable(parseFailuresFromOperationsStates(response.getOperationStates(), response.getFailureReason()))
        );
    }

    public DatalakeBackupStatusResponse convert(BackupDatalakeStatusResponse response) {
        return new DatalakeBackupStatusResponse(response.getBackupId(),
                State.valueOf(response.getOverallState()),
                Optional.ofNullable(parseFailuresFromOperationsStates(response.getOperationStates(), response.getFailureReason()))
        );
    }

    public DatalakeRestoreStatusResponse convert(RestoreDatalakeStatusResponse response) {
        return new DatalakeRestoreStatusResponse(response.getBackupId(), response.getRestoreId(),
                State.valueOf(response.getOverallState()),
                Optional.ofNullable(parseFailuresFromOperationsStates(response.getOperationStates(), response.getFailureReason()))
        );
    }

    private String parseFailuresFromOperationsStates(InternalBackupRestoreState operationStates, String legacyFailureReason) {
        String failure;
        if (operationStates != null && !operationStates.equals(InternalBackupRestoreState.getDefaultInstance())) {
            StringBuilder failureStringBuilder = new StringBuilder();
            getFailure(OperationEnum.STOP_SERVICES.description(), operationStates.getAdminOperations().getStopServices())
                    .ifPresent(failureStringBuilder::append);
            getFailure(OperationEnum.START_SERVICES.description(), operationStates.getAdminOperations().getStartServices())
                    .ifPresent(failureStringBuilder::append);
            parseHbaseFailure(operationStates.getHbase(), failureStringBuilder);
            parseSolrFailure(operationStates.getSolr(), failureStringBuilder);
            getFailure(OperationEnum.STORAGE_PERMISSION_VALIDATION.description(), operationStates.getAdminOperations().getPrecheckStoragePermission())
                    .ifPresent(failureStringBuilder::append);
            getFailure(OperationEnum.RANGER_AUDIT_COLLECTION_VALIDATION.description(), operationStates.getAdminOperations().getPrecheckRangerAuditValidation())
                    .ifPresent(failureStringBuilder::append);
            getFailure(OperationEnum.DATABASE.description(), operationStates.getDatabase().getDatabase())
                    .ifPresent(failureStringBuilder::append);

            String failureString = failureStringBuilder.toString();
            failure = StringUtils.isNotBlank(failureString) ? failureString.substring(0, failureString.lastIndexOf(", ")) : null;
        } else {
            failure = StringUtils.isNotBlank(legacyFailureReason) ? legacyFailureReason : null;
        }

        if (StringUtils.isNotBlank(failure)) {
            LOGGER.error("Found error on backup/restore operation: {}", failure);
        }
        return failure;
    }

    private void parseHbaseFailure(HbaseBackupRestoreState hbase, StringBuilder failureString) {
        if (hbase != null) {
            List<BackupRestoreOperationStatus> allFailureReasons = List.of(
                    hbase.getAtlasJanusTable(),
                    hbase.getAtlasEntityAuditEventTable()
            );
            if (StringUtils.isNotEmpty(hbase.getAtlasJanusTable().getFailureReason()) && areAllFailuresTheSame(allFailureReasons)) {
                getFailure(OperationEnum.HBASE.description(), hbase.getAtlasJanusTable())
                        .ifPresent(failureString::append);
            } else {
                getFailure(OperationEnum.HBASE_ATLAS_JANUS.description(), hbase.getAtlasJanusTable())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.HBASE_ATLAS_AUDIT.description(), hbase.getAtlasEntityAuditEventTable())
                        .ifPresent(failureString::append);
            }
        }
    }

    private void parseSolrFailure(SolrBackupRestoreState solr, StringBuilder failureString) {
        if (solr != null) {
            List<BackupRestoreOperationStatus> allFailureReasons = List.of(
                    solr.getEdgeIndexCollection(),
                    solr.getFulltextIndexCollection(),
                    solr.getRangerAuditsCollection(),
                    solr.getVertexIndexCollection()
            );
            if (StringUtils.isNotEmpty(solr.getEdgeIndexCollection().getFailureReason()) && areAllFailuresTheSame(allFailureReasons)) {
                getFailure(OperationEnum.SOLR.description(), solr.getEdgeIndexCollection())
                        .ifPresent(failureString::append);
            } else {
                getFailure(OperationEnum.SOLR_EDGE_INDEX.description(), solr.getEdgeIndexCollection())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.SOLR_FULLTEXT_INDEX.description(), solr.getFulltextIndexCollection())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.SOLR_RANGER_AUDITS.description(), solr.getRangerAuditsCollection())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.SOLR_VERTEX_INDEX.description(), solr.getVertexIndexCollection())
                        .ifPresent(failureString::append);
            }
            List<BackupRestoreOperationStatus> allDeleteFailureReasons = List.of(
                    solr.getEdgeIndexCollectionDelete(),
                    solr.getFulltextIndexCollectionDelete(),
                    solr.getRangerAuditsCollectionDelete(),
                    solr.getVertexIndexCollectionDelete()
            );
            if (StringUtils.isNotEmpty(solr.getEdgeIndexCollectionDelete().getFailureReason()) && areAllFailuresTheSame(allDeleteFailureReasons)) {
                getFailure(OperationEnum.SOLR_DELETE.description(), solr.getEdgeIndexCollectionDelete())
                        .ifPresent(failureString::append);
            } else {
                getFailure(OperationEnum.SOLR_EDGE_INDEX_DELETE.description(), solr.getEdgeIndexCollectionDelete())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.SOLR_FULLTEXT_INDEX_DELETE.description(), solr.getFulltextIndexCollectionDelete())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.SOLR_RANGER_AUDITS_DELETE.description(), solr.getRangerAuditsCollectionDelete())
                        .ifPresent(failureString::append);
                getFailure(OperationEnum.SOLR_VERTEX_INDEX_DELETE.description(), solr.getVertexIndexCollectionDelete())
                        .ifPresent(failureString::append);
            }
        }
    }

    private Optional<String> getFailure(String operationName, BackupRestoreOperationStatus status) {
        if (status != null && (FAILED_STATE.equals(status.getStatus()) || VALIDATION_FAILED.equals(status.getStatus()))) {
            return Optional.of(operationName + ": " + status.getFailureReason() + ", ");
        }
        return Optional.empty();
    }

    private boolean areAllFailuresTheSame(List<BackupRestoreOperationStatus> statuses) {
        return statuses.stream()
                .filter(Objects::nonNull)
                .map(BackupRestoreOperationStatus::getFailureReason)
                .allMatch(failureReason -> statuses.get(0).getFailureReason().equals(failureReason));
    }
}
