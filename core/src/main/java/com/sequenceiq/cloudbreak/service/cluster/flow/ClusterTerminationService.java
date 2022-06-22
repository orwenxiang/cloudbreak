package com.sequenceiq.cloudbreak.service.cluster.flow;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.DetailedStackStatus;
import com.sequenceiq.cloudbreak.cluster.service.ClusterComponentConfigProvider;
import com.sequenceiq.cloudbreak.cmtemplate.cloudstorage.CmCloudStorageConfigProvider;
import com.sequenceiq.cloudbreak.common.exception.NotFoundException;
import com.sequenceiq.cloudbreak.common.service.TransactionService;
import com.sequenceiq.cloudbreak.common.service.TransactionService.TransactionExecutionException;
import com.sequenceiq.cloudbreak.domain.FileSystem;
import com.sequenceiq.cloudbreak.domain.RDSConfig;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.domain.stack.cluster.ClusterComponent;
import com.sequenceiq.cloudbreak.domain.stack.cluster.host.HostGroup;
import com.sequenceiq.cloudbreak.service.ComponentConfigProviderService;
import com.sequenceiq.cloudbreak.service.cluster.ClusterService;
import com.sequenceiq.cloudbreak.service.hostgroup.HostGroupService;
import com.sequenceiq.cloudbreak.service.rdsconfig.RdsConfigService;
import com.sequenceiq.cloudbreak.service.stack.flow.TerminationFailedException;
import com.sequenceiq.cloudbreak.template.filesystem.BaseFileSystemConfigurationsView;
import com.sequenceiq.cloudbreak.template.filesystem.FileSystemConfigurationsViewProvider;
import com.sequenceiq.cloudbreak.template.filesystem.FileSystemConfigurator;
import com.sequenceiq.common.api.cloudstorage.query.ConfigQueryEntries;
import com.sequenceiq.common.model.FileSystemType;

@Component
public class ClusterTerminationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterTerminationService.class);

    private static final String DELIMITER = "_";

    @Inject
    private ClusterService clusterService;

    @Inject
    private HostGroupService hostGroupService;

    @Resource
    private Map<FileSystemType, FileSystemConfigurator<BaseFileSystemConfigurationsView>> fileSystemConfigurators;

    @Inject
    private ComponentConfigProviderService componentConfigProviderService;

    @Inject
    private RdsConfigService rdsConfigService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private FileSystemConfigurationsViewProvider fileSystemConfigurationsViewProvider;

    @Inject
    private CmCloudStorageConfigProvider cmCloudStorageConfigProvider;

    @Inject
    private ClusterComponentConfigProvider clusterComponentConfigProvider;

    public void finalizeClusterTermination(Long clusterId, boolean force) throws TransactionExecutionException {
        Cluster cluster = clusterService.findOneWithLists(clusterId)
                .orElseThrow(NotFoundException.notFound("cluster", clusterId));
        Set<RDSConfig> rdsConfigs = cluster.getRdsConfigs();
        Long stackId = cluster.getStack().getId();
        String terminatedName = cluster.getName() + DELIMITER + new Date().getTime();
        cluster.setName(terminatedName);
        clusterService.cleanupCluster(cluster);
        FileSystem fs = cluster.getFileSystem();
        if (fs != null) {
            deleteFileSystemResources(stackId, fs, force);
        }
        cluster.setBlueprint(null);
        cluster.setCustomConfigurations(null);
        clusterService.updateClusterStatusByStackId(stackId, DetailedStackStatus.CLUSTER_DELETE_COMPLETED);
        cluster.setFileSystem(null);
        transactionService.required(() -> {
            deleteClusterHostGroupsWithItsMetadata(cluster);
            rdsConfigService.deleteDefaultRdsConfigs(rdsConfigs);
            componentConfigProviderService.deleteComponentsForStack(stackId);
            deleteClusterComponentAndItsAudits(cluster.getComponents(), clusterId);
            return null;
        });
    }

    private void  deleteClusterComponentAndItsAudits(Set<ClusterComponent> componentsToDelete, Long clusterId) {
        LOGGER.debug("About to clean up the following ClusterComponents for cluster [id: {}] {}", componentsToDelete, clusterId);
        clusterComponentConfigProvider.deleteClusterComponents(componentsToDelete);
        LOGGER.debug("ClusterComponent cleanup has finished.");
        LOGGER.debug("About to clean up ClusterComponent history for cluster [id: {}]", clusterId);
        clusterComponentConfigProvider.cleanUpAudit(clusterId);
        LOGGER.debug("ClusterComponent history cleanup has finished.");
    }

    private void deleteClusterHostGroupsWithItsMetadata(Cluster cluster) {
        Set<HostGroup> hostGroups = hostGroupService.findHostGroupsInCluster(cluster.getId());
        hostGroupService.deleteAll(hostGroups);
        cluster.getHostGroups().clear();
        cluster.getContainers().clear();
        clusterService.save(cluster);
    }

    private void deleteFileSystemResources(Long stackId, FileSystem fileSystem, boolean force) {
        try {
            FileSystemConfigurator<BaseFileSystemConfigurationsView> fsConfigurator = fileSystemConfigurators.get(fileSystem.getType());
            ConfigQueryEntries configQueryEntries = cmCloudStorageConfigProvider.getConfigQueryEntries();
            BaseFileSystemConfigurationsView fsConfiguration
                    = fileSystemConfigurationsViewProvider.propagateConfigurationsView(fileSystem, configQueryEntries);
            if (fsConfiguration != null) {
                fsConfiguration.setStorageContainer("cloudbreak" + stackId);
                fsConfigurator.deleteResources(fsConfiguration);
            }
        } catch (Exception e) {
            if (force) {
                LOGGER.error("Error during file system deletion, moving on based on the force flag, ", e);
            } else {
                throw new TerminationFailedException("File system resources could not be deleted: ", e);
            }
        }
    }
}
