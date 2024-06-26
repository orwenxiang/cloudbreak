package com.sequenceiq.cloudbreak.cmtemplate.configproviders.nifi;

import static com.sequenceiq.cloudbreak.cmtemplate.CMRepositoryVersionUtil.CLOUDERA_STACK_VERSION_7_2_10;
import static com.sequenceiq.cloudbreak.cmtemplate.CMRepositoryVersionUtil.isVersionNewerOrEqualThanLimited;
import static com.sequenceiq.cloudbreak.cmtemplate.configproviders.ConfigUtils.config;
import static com.sequenceiq.cloudbreak.template.VolumeUtils.buildSingleVolumePath;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cloudera.api.swagger.model.ApiClusterTemplateConfig;
import com.sequenceiq.cloudbreak.cmtemplate.CmHostGroupRoleConfigProvider;
import com.sequenceiq.cloudbreak.template.TemplatePreparationObject;
import com.sequenceiq.cloudbreak.template.views.HostgroupView;

@Component
public class NifiVolumeConfigProvider implements CmHostGroupRoleConfigProvider {
    private static final int FIRST_VOLUME = 1;

    private static final int SECOND_VOLUME = 2;

    private static final int THIRD_VOLUME = 3;

    private static final int FOURTH_VOLUME = 4;

    @Override
    public List<ApiClusterTemplateConfig> getRoleConfigs(String roleType, HostgroupView hostGroupView, TemplatePreparationObject source) {
        List<ApiClusterTemplateConfig> roleConfigs = new ArrayList<>();
        String cdhVersion = source.getBlueprintView().getProcessor().getStackVersion() == null ?
                "" : source.getBlueprintView().getProcessor().getStackVersion();

        if (source.getProductDetailsView() != null) {
            if (source.getProductDetailsView().getProducts().stream()
                    .filter(product -> product.getName().equals("CFM"))
                    .anyMatch(product -> {
                        String version = product.getVersion();
                        return "1.0.0.0".equals(version) || "1.0.1.0".equals(version);
                    })) {
                return List.of();
            }
        }

        Integer volumeCount = Objects.nonNull(hostGroupView) ? hostGroupView.getVolumeCount() : 0;
        int flowFileVolInd = FIRST_VOLUME;
        int contentVolInd = SECOND_VOLUME;
        int provenanceVolInd = THIRD_VOLUME;
        int logDirVolInd = FOURTH_VOLUME;
        int databaseVolInd = FOURTH_VOLUME;
        int workingDirVolInd = FOURTH_VOLUME;
        if (volumeCount == SECOND_VOLUME) {
            flowFileVolInd = FIRST_VOLUME;
            provenanceVolInd = FIRST_VOLUME;
            databaseVolInd = FIRST_VOLUME;
            contentVolInd = SECOND_VOLUME;
            logDirVolInd = SECOND_VOLUME;
            workingDirVolInd = SECOND_VOLUME;
        } else if (volumeCount == THIRD_VOLUME) {
            flowFileVolInd = FIRST_VOLUME;
            databaseVolInd = FIRST_VOLUME;
            provenanceVolInd = SECOND_VOLUME;
            contentVolInd = THIRD_VOLUME;
            logDirVolInd = THIRD_VOLUME;
            workingDirVolInd = THIRD_VOLUME;
        }

        roleConfigs.add(config("nifi.flowfile.repository.directory", buildSingleVolumePath(flowFileVolInd, volumeCount, "flowfile-repo")));
        roleConfigs.add(config("nifi.content.repository.directory.default", buildSingleVolumePath(contentVolInd, volumeCount, "content-repo")));
        roleConfigs.add(config("nifi.provenance.repository.directory.default", buildSingleVolumePath(provenanceVolInd, volumeCount, "provenance-repo")));
        roleConfigs.add(config("log_dir", buildSingleVolumePath(logDirVolInd, volumeCount, "nifi-log")));
        roleConfigs.add(config("nifi.database.directory", buildSingleVolumePath(databaseVolInd, volumeCount, "database-dir")));

        if (isVersionNewerOrEqualThanLimited(cdhVersion, CLOUDERA_STACK_VERSION_7_2_10)) {
            roleConfigs.add(config("nifi.working.directory", buildSingleVolumePath(workingDirVolInd, volumeCount, "working-dir")));
        }

        return roleConfigs;
    }

    @Override
    public String getServiceType() {
        return "NIFI";
    }

    @Override
    public Set<String> getRoleTypes() {
        return Set.of("NIFI_NODE");
    }

    @Override
    public boolean sharedRoleType(String roleType) {
        return false;
    }
}
