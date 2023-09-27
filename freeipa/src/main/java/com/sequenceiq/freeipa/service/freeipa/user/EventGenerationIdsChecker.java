package com.sequenceiq.freeipa.service.freeipa.user;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.freeipa.entity.Stack;
import com.sequenceiq.freeipa.entity.UserSyncStatus;
import com.sequenceiq.freeipa.service.freeipa.user.model.UmsEventGenerationIds;

@Component
public class EventGenerationIdsChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventGenerationIdsChecker.class);

    public boolean isInSync(UserSyncStatus userSyncStatus, UmsEventGenerationIds currentGeneration) {
        boolean inSync = userSyncStatus != null &&
                userSyncStatus.getUmsEventGenerationIds() != null;
        if (inSync) {
            try {
                UmsEventGenerationIds lastUmsEventGenerationIds = userSyncStatus.getUmsEventGenerationIds().get(UmsEventGenerationIds.class);
                inSync = currentGeneration.equals(lastUmsEventGenerationIds);
            } catch (IOException e) {
                LOGGER.warn("Failed to retrieve UmsEventGenerationIds for Environment {} in Account {}. Assuming not in sync",
                        getEnvironmentCrn(userSyncStatus), getAccountId(userSyncStatus));
                inSync = false;
            }
        }
        LOGGER.debug("Environment {} in Account {} {} in sync", getEnvironmentCrn(userSyncStatus), getAccountId(userSyncStatus), inSync ? "is" : "is not");
        return inSync;
    }

    private String getEnvironmentCrn(UserSyncStatus userSyncStatus) {
        Stack stack = userSyncStatus != null ? userSyncStatus.getStack() : null;
        if (stack != null) {
            return stack.getEnvironmentCrn();
        }
        return null;
    }

    private String getAccountId(UserSyncStatus userSyncStatus) {
        Stack stack = userSyncStatus != null ? userSyncStatus.getStack() : null;
        if (stack != null) {
            return stack.getAccountId();
        }
        return null;
    }

}
