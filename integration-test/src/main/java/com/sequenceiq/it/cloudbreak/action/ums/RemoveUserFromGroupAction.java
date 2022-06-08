package com.sequenceiq.it.cloudbreak.action.ums;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.cloudbreak.auth.crn.RegionAwareInternalCrnGeneratorFactory;
import com.sequenceiq.it.cloudbreak.UmsClient;
import com.sequenceiq.it.cloudbreak.action.Action;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.ums.UmsGroupTestDto;
import com.sequenceiq.it.cloudbreak.log.Log;

public class RemoveUserFromGroupAction implements Action<UmsGroupTestDto, UmsClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveUserFromGroupAction.class);

    private final String groupName;

    private final String memberCrn;

    private final RegionAwareInternalCrnGeneratorFactory regionAwareInternalCrnGeneratorFactory;

    public RemoveUserFromGroupAction(String groupName, String memberCrn, RegionAwareInternalCrnGeneratorFactory regionAwareInternalCrnGeneratorFactory) {
        this.groupName = groupName;
        this.memberCrn = memberCrn;
        this.regionAwareInternalCrnGeneratorFactory = regionAwareInternalCrnGeneratorFactory;
    }

    @Override
    public UmsGroupTestDto action(TestContext testContext, UmsGroupTestDto testDto, UmsClient client) throws Exception {
        String accountId = testDto.getRequest().getAccountId();
        testDto.withName(groupName);
        testDto.withMember(memberCrn);
        Log.when(LOGGER, format(" Removing user '%s' from group '%s' at account '%s'. ", memberCrn, groupName, accountId));
        Log.whenJson(LOGGER, format(" Remove user from group request:%n "), testDto.getRequest());
        client.getDefaultClient().removeMemberFromGroup(accountId, groupName, memberCrn, regionAwareInternalCrnGeneratorFactory);
        LOGGER.info(format(" User '%s' has been removed from group '%s' at account '%s'. ", memberCrn, groupName, accountId));
        Log.when(LOGGER, format(" User '%s' has been removed from group '%s' at account '%s'. ", memberCrn, groupName, accountId));
        return testDto;
    }
}
