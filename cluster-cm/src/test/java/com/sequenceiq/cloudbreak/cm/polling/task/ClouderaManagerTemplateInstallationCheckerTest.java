package com.sequenceiq.cloudbreak.cm.polling.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cloudera.api.swagger.CommandsResourceApi;
import com.cloudera.api.swagger.client.ApiClient;
import com.cloudera.api.swagger.client.ApiException;
import com.cloudera.api.swagger.model.ApiCommand;
import com.cloudera.api.swagger.model.ApiCommandList;
import com.sequenceiq.cloudbreak.cm.ClouderaManagerOperationFailedException;
import com.sequenceiq.cloudbreak.cm.client.ClouderaManagerApiPojoFactory;
import com.sequenceiq.cloudbreak.cm.exception.CloudStorageConfigurationFailedException;
import com.sequenceiq.cloudbreak.cm.polling.ClouderaManagerCommandPollerObject;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.structuredevent.event.CloudbreakEventService;

class ClouderaManagerTemplateInstallationCheckerTest {

    private static final String TEMPLATE_INSTALL_NAME = "TemplateInstall";

    private static final String ADD_REPOSITORIES_NAME = "AddRepositories";

    private static final String DEPLOY_PARCELS_NAME = "DeployParcels";

    private static final String FIRST_RUN_NAME = "First Run";

    private static final String AUDIT_DIR_COMMAND_NAME = "CreateRangerKafkaPluginAuditDirCommand";

    private ApiClient apiClient = Mockito.mock(ApiClient.class);

    private ClouderaManagerApiPojoFactory clouderaManagerApiPojoFactory = Mockito.mock(ClouderaManagerApiPojoFactory.class);

    private CommandsResourceApi commandsResourceApi = Mockito.mock(CommandsResourceApi.class);

    private ApiCommand apiCommand = Mockito.mock(ApiCommand.class);

    private CloudbreakEventService cloudbreakEventService = Mockito.mock(CloudbreakEventService.class);

    private ClouderaManagerTemplateInstallationChecker underTest
            = new ClouderaManagerTemplateInstallationChecker(clouderaManagerApiPojoFactory, cloudbreakEventService);

    private ClouderaManagerCommandPollerObject pollerObject;

    @BeforeEach
    void setUp() throws ApiException {
        when(commandsResourceApi.readCommand(any())).thenReturn(apiCommand);
        when(clouderaManagerApiPojoFactory.getCommandsResourceApi(eq(apiClient))).thenReturn(commandsResourceApi);
        pollerObject = new ClouderaManagerCommandPollerObject(new Stack(), apiClient, 1);
    }

    @Test
    void checkStatusWithSubCommandMessage() throws ApiException {
        ApiCommand addRepositoriesCmd = addReposCmd();
        ApiCommand deployParcelsCmd = deployParcelsCmd().success(Boolean.FALSE)
                .resultMessage("Failed to deploy parcels");
        ApiCommand templateInstallCmd = templateInstallCmd(addRepositoriesCmd, deployParcelsCmd);
        expectReadCommandForFailedCommands(templateInstallCmd);

        ClouderaManagerOperationFailedException ex = assertThrows(ClouderaManagerOperationFailedException.class, () -> underTest.checkStatus(pollerObject));
        String expected = expectMessageForCommands(deployParcelsCmd);
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void checkStatusWithMultiLevelMessage() throws ApiException {
        ApiCommand addRepositoriesCmd = addReposCmd();
        ApiCommand deployParcelsCmd = deployParcelsCmd();
        ApiCommand firstRunCmd = firstRunCmd().success(Boolean.FALSE)
                .resultMessage("Failed to perform First Run of services.");
        ApiCommand templateInstallCmd = templateInstallCmd(addRepositoriesCmd, deployParcelsCmd, firstRunCmd)
                .resultMessage("Failed to import cluster template");
        expectReadCommandForFailedCommands(templateInstallCmd);

        ClouderaManagerOperationFailedException ex = assertThrows(ClouderaManagerOperationFailedException.class, () -> underTest.checkStatus(pollerObject));
        String expected = expectMessageForCommands(firstRunCmd);
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void checkStatusWithMultipleFailures() throws ApiException {
        ApiCommand addRepositoriesCmd = addReposCmd().success(Boolean.FALSE)
                .resultMessage("Permission denied");
        ApiCommand deployParcelsCmd = deployParcelsCmd().success(Boolean.FALSE)
                .resultMessage("Host not found");
        ApiCommand firstRunCmd = firstRunCmd().success(Boolean.FALSE)
                .resultMessage("Failed to perform First Run of services.");
        ApiCommand templateInstallCmd = templateInstallCmd(addRepositoriesCmd, deployParcelsCmd, firstRunCmd)
                .resultMessage("Failed to import cluster template");
        expectReadCommandForFailedCommands(templateInstallCmd);

        ClouderaManagerOperationFailedException ex = assertThrows(ClouderaManagerOperationFailedException.class, () -> underTest.checkStatus(pollerObject));
        String expected = expectMessageForCommands(addRepositoriesCmd, deployParcelsCmd, firstRunCmd);
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void checkStatusWithTopLevelMessage() throws ApiException {
        ApiCommand addRepositoriesCmd = addReposCmd();
        ApiCommand deployParcelsCmd = deployParcelsCmd();
        ApiCommand templateInstallCmd = templateInstallCmd(addRepositoriesCmd, deployParcelsCmd)
                .resultMessage("IllegalArgumentException: Unknown configuration attribute 'process_autorestart_enabled'.");
        expectReadCommandForFailedCommands(templateInstallCmd);

        ClouderaManagerOperationFailedException ex = assertThrows(ClouderaManagerOperationFailedException.class, () -> underTest.checkStatus(pollerObject));
        String expected = expectMessageForCommands(templateInstallCmd);
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void checkStatusWithActiveCommands() throws ApiException {
        ApiCommand addRepositoriesCmd = addReposCmd().success(Boolean.FALSE)
                .resultMessage("Permission denied");
        ApiCommand deployParcelsCmd = deployParcelsCmd().success(Boolean.FALSE)
                .resultMessage("Host not found");
        ApiCommand firstRunCmd = firstRunCmd().active(Boolean.TRUE)
                .resultMessage("Actually this has not finished yet...");
        ApiCommand templateInstallCmd = templateInstallCmd(addRepositoriesCmd, deployParcelsCmd, firstRunCmd)
                .resultMessage("Failed to import cluster template");
        expectReadCommandForFailedCommands(templateInstallCmd);

        ClouderaManagerOperationFailedException ex = assertThrows(ClouderaManagerOperationFailedException.class, () -> underTest.checkStatus(pollerObject));
        String expected = expectMessageForCommands(addRepositoriesCmd, deployParcelsCmd, firstRunCmd);
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void testCloudStorageFailure() throws ApiException {
        ApiCommand auditDirCmd = auditDirCmd().success(Boolean.FALSE)
                .resultMessage("Aborted");
        ApiCommand deployParcelsCmd = deployParcelsCmd().success(Boolean.FALSE)
                .resultMessage("Host not found");
        ApiCommand firstRunCmd = firstRunCmd().success(Boolean.FALSE)
                .resultMessage("Failed to perform First Run of services.");
        ApiCommand templateInstallCmd = templateInstallCmd(auditDirCmd, deployParcelsCmd, firstRunCmd)
                .resultMessage("Failed to import cluster template");
        expectReadCommandForFailedCommands(templateInstallCmd);

        CloudStorageConfigurationFailedException ex = assertThrows(CloudStorageConfigurationFailedException.class, () -> underTest.checkStatus(pollerObject));
        String expected = expectMessageForCommands(auditDirCmd, deployParcelsCmd, firstRunCmd);
        assertEquals(expected, ex.getMessage());
    }

    private void expectReadCommandForFailedCommands(ApiCommand templateInstallCmd) throws ApiException {
        Map<Integer, ApiCommand> failedCommands = Stream.concat(
                Stream.of(templateInstallCmd),
                templateInstallCmd.getChildren().getItems().stream()
        )
                .filter(cmd -> (cmd.getActive() != null && cmd.getActive()) || (cmd.getSuccess() != null && !cmd.getSuccess()))
                .collect(Collectors.toMap(ApiCommand::getId, Function.identity()));

        when(commandsResourceApi.readCommand(any(Integer.class))).thenAnswer(invocation -> {
            Integer cmdId = invocation.getArgument(0);
            ApiCommand cmd = failedCommands.get(cmdId);
            if (cmd == null) {
                throw new IllegalArgumentException("Unexpected argument for readCommand: " + cmdId);
            }
            return cmd;
        });
    }

    private String expectMessageForCommands(ApiCommand... commands) {
        String msgFormat = "Installation of CDP with Cloudera Manager has failed: [%s]";
        String cmdFormat = "Command [%s], with id [%d] failed: %s";
        return String.format(msgFormat,
                Arrays.stream(commands)
                        .map(cmd -> String.format(cmdFormat, cmd.getName(), cmd.getId(), cmd.getResultMessage()))
                        .collect(Collectors.joining(", "))
        );
    }

    private ApiCommand templateInstallCmd(ApiCommand... children) {
        return cmd(1, TEMPLATE_INSTALL_NAME)
                .active(Boolean.FALSE)
                .success(Boolean.FALSE)
                .children(new ApiCommandList().items(List.of(children)));
    }

    private ApiCommand auditDirCmd() {
        return cmd(14, AUDIT_DIR_COMMAND_NAME);
    }

    private ApiCommand addReposCmd() {
        return cmd(11, ADD_REPOSITORIES_NAME);
    }

    private ApiCommand deployParcelsCmd() {
        return cmd(12, DEPLOY_PARCELS_NAME);
    }

    private ApiCommand firstRunCmd() {
        return cmd(13, FIRST_RUN_NAME);
    }

    private ApiCommand cmd(Integer id, String name) {
        return new ApiCommand().id(id).name(name).active(Boolean.FALSE).success(Boolean.TRUE);
    }
}