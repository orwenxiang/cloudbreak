package com.sequenceiq.it.cloudbreak.newway.action.blueprint;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.log;
import static com.sequenceiq.it.cloudbreak.newway.log.Log.logJSON;
import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.it.cloudbreak.newway.CloudbreakClient;
import com.sequenceiq.it.cloudbreak.newway.action.Action;
import com.sequenceiq.it.cloudbreak.newway.context.TestContext;
import com.sequenceiq.it.cloudbreak.newway.entity.blueprint.BlueprintEntity;

public class BlueprintDeleteAction implements Action<BlueprintEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlueprintDeleteAction.class);

    @Override
    public BlueprintEntity action(TestContext testContext, BlueprintEntity entity, CloudbreakClient client) throws Exception {
        log(LOGGER, format(" Name: %s", entity.getRequest().getName()));
        logJSON(LOGGER, format(" Blueprint delete request:%n"), entity.getRequest());
        entity.setResponse(
                client.getCloudbreakClient()
                        .blueprintV4Endpoint()
                        .delete(client.getWorkspaceId(), entity.getName()));
        logJSON(LOGGER, format(" Blueprint deleted successfully:%n"), entity.getResponse());
        log(LOGGER, format(" ID: %s", entity.getResponse().getId()));

        return entity;
    }

}