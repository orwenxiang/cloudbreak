package com.sequenceiq.it.cloudbreak.testcase.mock;

import java.util.Arrays;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sequenceiq.cloudbreak.api.endpoint.v4.database.base.DatabaseType;
import com.sequenceiq.it.cloudbreak.assertion.database.RedbeamsDatabaseTestAssertion;
import com.sequenceiq.it.cloudbreak.client.RedbeamsDatabaseTestClient;
import com.sequenceiq.it.cloudbreak.context.Description;
import com.sequenceiq.it.cloudbreak.context.MockedTestContext;
import com.sequenceiq.it.cloudbreak.context.RunningParameter;
import com.sequenceiq.it.cloudbreak.context.TestCaseDescription;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.dto.database.RedbeamsDatabaseTestDto;

// import com.sequenceiq.cloudbreak.api.endpoint.v4.database.requests.DatabaseV4Request;

public class DatabaseTest extends AbstractMockTest {

    private static final String DB_TYPE_PROVIDER = "databaseTypeTestProvider";

    private static final String INVALID_ATTRIBUTE_PROVIDER = "databaseInvalidAttributesTestProvider";

    private static final String DATABASE_PROTOCOL = "jdbc:postgresql://";

    private static final String DATABASE_HOST_PORT_DB = "somedb.com:5432/mydb";

    private static final String DATABASE_USERNAME = "username";

    private static final String DATABASE_PASSWORD = "password";

    private static final Class<MockedTestContext> TEST_CONTEXT_CLASS = MockedTestContext.class;

    @Inject
    private RedbeamsDatabaseTestClient databaseTestClient;

    @Override
    protected void setupTest(TestContext testContext) {
        createDefaultUser(testContext);
        createDefaultEnvironment(testContext);
    }

    // TODO: Update when redbeams service is ready
    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK, enabled = false)
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

    // TODO: Update when redbeams service is ready
    @Test(dataProvider = TEST_CONTEXT_WITH_MOCK, enabled = false)
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

    @DataProvider(name = DB_TYPE_PROVIDER)
    public Object[][] provideTypes() {
        List<DatabaseType> databaseTypeList = Arrays.asList(DatabaseType.values());
        Object[][] objects = new Object[databaseTypeList.size()][3];
        databaseTypeList
                .forEach(databaseType -> {
                    objects[databaseTypeList.indexOf(databaseType)][0] = getBean(TEST_CONTEXT_CLASS);
                    objects[databaseTypeList.indexOf(databaseType)][1] = databaseType;
                    objects[databaseTypeList.indexOf(databaseType)][2] =
                            new TestCaseDescription.TestCaseDescriptionBuilder()
                                    .given("there is a running cloudbreak")
                                    .when("sending database create request with databaseType '" + databaseType + '\'')
                                    .then("creation should be successful");
                });
        return objects;
    }

    @DataProvider(name = INVALID_ATTRIBUTE_PROVIDER)
    public Object[][] provideInvalidAttributes() {
        return new Object[][]{
                {
                        getBean(TEST_CONTEXT_CLASS),
                        getLongNameGenerator().stringGenerator(51),
                        DATABASE_USERNAME, DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "The length of the name has to be in range of 4 to 50",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling database create endpoint with 51 characters long name")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        "abc",
                        DATABASE_USERNAME,
                        DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "The length of the name has to be in range of 4 to 50",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling database create endpoint with 3 characcters long name")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        "a-@#$%|:&*;",
                        DATABASE_USERNAME,
                        DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "The database's name can only contain lowercase alphanumeric characters and "
                                + "hyphens and has start with an alphanumeric character",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling database create endpoint with invalid characters in the name")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        resourcePropertyProvider().getName(),
                        null,
                        DATABASE_PASSWORD,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "must not be null",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling database create endpoint with 'null' connectionUserName")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        resourcePropertyProvider().getName(),
                        DATABASE_USERNAME,
                        null,
                        DATABASE_PROTOCOL + DATABASE_HOST_PORT_DB,
                        "must not be null",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling database create endpoint with 'null' connectionPassword")
                                .then("a BadRequestException should be returned")
                },
                {
                        getBean(TEST_CONTEXT_CLASS),
                        resourcePropertyProvider().getName(),
                        DATABASE_USERNAME,
                        DATABASE_PASSWORD,
                        DATABASE_HOST_PORT_DB,
                        "Unsupported database type",
                        new TestCaseDescription.TestCaseDescriptionBuilder()
                                .given("there is a running cloudbreak")
                                .when("calling database create endpoint with unsupported database type")
                                .then("a BadRequestException should be returned")
                }
        };
    }
}
