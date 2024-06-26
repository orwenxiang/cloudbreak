package com.sequenceiq.it.cloudbreak.testcase.mock;

import static com.sequenceiq.it.cloudbreak.context.RunningParameter.expectedMessage;

import jakarta.ws.rs.BadRequestException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sequenceiq.it.cloudbreak.action.stack.StackTestAction;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.MockedTestContext;
import com.sequenceiq.it.cloudbreak.dto.stack.StackTestDto;

public class ClusterCreationTest extends AbstractMockTest {

    @BeforeMethod
    public void beforeMethod(Object[] data) {
        createDefaultUser((MockedTestContext) data[0]);
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "a stack without network and placement",
            when = "post is called",
            then = "should bad request exception")
    public void createClusterWithoutNetworkAndPlacementShouldBadRequestException(MockedTestContext testContext) {
        testContext
                .given(StackTestDto.class)
                .withEmptyNetwork()
                .withEmptyPlacement()
                .whenException(StackTestAction::create, BadRequestException.class, expectedMessage("Network must be specified!"))
                .validate();
    }
}
