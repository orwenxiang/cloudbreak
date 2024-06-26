package com.sequenceiq.it.cloudbreak.testcase.mock;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import org.testng.annotations.Test;

import com.sequenceiq.it.cloudbreak.assertion.database.RedbeamsDatabaseTestAssertion;
import com.sequenceiq.it.cloudbreak.client.RedbeamsDatabaseTestClient;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.MockedTestContext;
import com.sequenceiq.it.cloudbreak.context.RunningParameter;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.database.RedbeamsDatabaseTestDto;

public class RedbeamsDatabaseTest extends AbstractMockTest {

    @Inject
    private RedbeamsDatabaseTestClient databaseTestClient;

    @Override
    protected void setupTest(TestContext testContext) {
        createDefaultUser(testContext);
        createDefaultCredential(testContext);
        createDefaultEnvironment(testContext);
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a prepared database",
            when = "the database is deleted and then a create request is sent with the same database name",
            then = "the database should be created again")
    public void createAndDeleteAndCreateWithSameNameThenShouldRecreatedDatabase(MockedTestContext testContext) {
        String databaseName = resourcePropertyProvider().getName();
        testContext
                .given(RedbeamsDatabaseTestDto.class)
                .withName(databaseName)
                .when(databaseTestClient.createV4(), RunningParameter.key(databaseName))
                .when(databaseTestClient.listV4(), RunningParameter.key(databaseName))
                .then(RedbeamsDatabaseTestAssertion.containsDatabaseName(databaseName, 1), RunningParameter.key(databaseName))
                .when(databaseTestClient.deleteV4(), RunningParameter.key(databaseName))
                .when(databaseTestClient.listV4(), RunningParameter.key(databaseName))
                .then(RedbeamsDatabaseTestAssertion.containsDatabaseName(databaseName, 0), RunningParameter.key(databaseName))
                .when(databaseTestClient.createV4(), RunningParameter.key(databaseName))
                .when(databaseTestClient.listV4(), RunningParameter.key(databaseName))
                .then(RedbeamsDatabaseTestAssertion.containsDatabaseName(databaseName, 1), RunningParameter.key(databaseName))
                .validate();
    }

    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK)
    @Description(
            given = "there is a prepared database",
            when = "when a database create request is sent with the same database name",
            then = "the create should return a BadRequestException")
    public void createAndCreateWithSameNameThenShouldThrowBadRequestException(MockedTestContext testContext) {
        String databaseName = resourcePropertyProvider().getName();
        testContext
                .given(RedbeamsDatabaseTestDto.class)
                .withName(databaseName)
                .when(databaseTestClient.createV4(), RunningParameter.key(databaseName))
                .when(databaseTestClient.listV4(), RunningParameter.key(databaseName))
                .then(RedbeamsDatabaseTestAssertion.containsDatabaseName(databaseName, 1), RunningParameter.key(databaseName))
                .whenException(databaseTestClient.createV4(), BadRequestException.class)
                .validate();
    }
}
