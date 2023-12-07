package com.sequenceiq.cloudbreak.converter.v4.stacks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.database.DatabaseAvailabilityType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.database.DatabaseAzureRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.database.DatabaseRequest;
import com.sequenceiq.cloudbreak.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.domain.stack.Database;
import com.sequenceiq.cloudbreak.service.database.EnvironmentDatabaseService;
import com.sequenceiq.common.model.AzureDatabaseType;
import com.sequenceiq.environment.api.v1.environment.model.response.DetailedEnvironmentResponse;

@ExtendWith(MockitoExtension.class)
class DatabaseRequestToDatabaseConverterTest {
    @Mock
    private EnvironmentDatabaseService environmentDatabaseService;

    @InjectMocks
    private DatabaseRequestToDatabaseConverter service;

    private DetailedEnvironmentResponse environmentResponse;

    @BeforeEach
    void setup() {
        environmentResponse = new DetailedEnvironmentResponse();
        lenient().when(environmentDatabaseService.validateOrModifyDatabaseTypeIfNeeded(eq(environmentResponse), nullable(AzureDatabaseType.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));
    }

    @Test
    void testConvertWithNullDBType() {
        DatabaseRequest databaseRequest = new DatabaseRequest();
        databaseRequest.setAvailabilityType(DatabaseAvailabilityType.NON_HA);

        Database database = service.convert(environmentResponse, CloudPlatform.AZURE, databaseRequest);

        assertNull(database.getAttributes().getMap().get(AzureDatabaseType.AZURE_DATABASE_TYPE_KEY));
        assertNull(database.getDatalakeDatabaseAvailabilityType());
    }

    @Test
    void testConvertWithAws() {
        DatabaseRequest databaseRequest = new DatabaseRequest();

        Database database = service.convert(environmentResponse, CloudPlatform.AWS, databaseRequest);

        assertNull(database.getAttributes());
        assertEquals(DatabaseAvailabilityType.NONE, database.getExternalDatabaseAvailabilityType());
        assertNull(database.getDatalakeDatabaseAvailabilityType());
    }

    @Test
    void testConvertWithAzureFlexibleServer() {
        DatabaseRequest databaseRequest = new DatabaseRequest();
        DatabaseAzureRequest databaseAzureRequest = new DatabaseAzureRequest();
        databaseAzureRequest.setAzureDatabaseType(AzureDatabaseType.FLEXIBLE_SERVER);
        databaseRequest.setDatabaseAzureRequest(databaseAzureRequest);
        databaseRequest.setAvailabilityType(DatabaseAvailabilityType.HA);

        Database database = service.convert(environmentResponse, CloudPlatform.AZURE, databaseRequest);

        assertEquals(AzureDatabaseType.FLEXIBLE_SERVER.name(), database.getAttributes().getMap().get(AzureDatabaseType.AZURE_DATABASE_TYPE_KEY));
    }

    @Test
    void testMapAzureAttributesInCaseOfEmbedded() {
        DatabaseRequest databaseRequest = new DatabaseRequest();
        DatabaseAzureRequest databaseAzureRequest = new DatabaseAzureRequest();
        databaseAzureRequest.setAzureDatabaseType(AzureDatabaseType.FLEXIBLE_SERVER);
        databaseRequest.setAvailabilityType(DatabaseAvailabilityType.NONE);
        databaseRequest.setDatabaseAzureRequest(databaseAzureRequest);

        Database result = service.convert(null, CloudPlatform.AZURE, databaseRequest);

        assertEquals(AzureDatabaseType.FLEXIBLE_SERVER.name(), result.getAttributes().getMap().get(AzureDatabaseType.AZURE_DATABASE_TYPE_KEY));
        assertEquals(DatabaseAvailabilityType.NONE, result.getExternalDatabaseAvailabilityType());
        assertNull(result.getDatalakeDatabaseAvailabilityType());
    }
}
