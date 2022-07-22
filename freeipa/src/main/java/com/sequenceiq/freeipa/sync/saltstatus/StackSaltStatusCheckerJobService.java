package com.sequenceiq.freeipa.sync.saltstatus;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.quartz.model.JobResource;
import com.sequenceiq.cloudbreak.quartz.saltstatuschecker.SaltStatusCheckerJobService;
import com.sequenceiq.freeipa.service.stack.StackService;

@Service
public class StackSaltStatusCheckerJobService extends SaltStatusCheckerJobService<StackSaltStatusCheckerJobAdapter> {

    @Inject
    private StackService stackService;

    public void schedule(Long stackId) {
        JobResource jobResource = stackService.getJobResource(stackId);
        schedule(new StackSaltStatusCheckerJobAdapter(jobResource));
    }
}
