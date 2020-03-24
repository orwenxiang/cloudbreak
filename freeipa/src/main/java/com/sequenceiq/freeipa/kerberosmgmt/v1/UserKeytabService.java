package com.sequenceiq.freeipa.kerberosmgmt.v1;

import static com.sequenceiq.cloudbreak.auth.altus.GrpcUmsClient.INTERNAL_ACTOR_CRN;
import static com.sequenceiq.freeipa.controller.exception.NotFoundException.notFound;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.sequenceiq.freeipa.client.FreeIpaClient;
import com.sequenceiq.freeipa.client.FreeIpaClientException;
import com.sequenceiq.freeipa.client.model.User;
import com.sequenceiq.freeipa.controller.exception.NotFoundException;
import com.sequenceiq.freeipa.service.freeipa.FreeIpaClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudera.thunderhead.service.usermanagement.UserManagementProto.ActorKerberosKey;
import com.cloudera.thunderhead.service.usermanagement.UserManagementProto.GetActorWorkloadCredentialsResponse;
import com.sequenceiq.cloudbreak.auth.altus.Crn;
import com.sequenceiq.cloudbreak.auth.altus.GrpcUmsClient;
import com.sequenceiq.cloudbreak.logger.MDCUtils;
import com.sequenceiq.freeipa.controller.exception.BadRequestException;
import com.sequenceiq.freeipa.kerberos.KerberosConfig;
import com.sequenceiq.freeipa.kerberos.KerberosConfigRepository;

@Service
public class UserKeytabService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserKeytabService.class);

    @Inject
    private KerberosConfigRepository kerberosConfigRepository;

    @Inject
    private GrpcUmsClient grpcUmsClient;

    @Inject
    private UserKeytabGenerator userKeytabGenerator;

    @Inject
    private FreeIpaClientFactory freeIpaClientFactory;

    private String getKerberosRealm(String accountId, String environmentCrn) {
        KerberosConfig krbConfig =  kerberosConfigRepository
                .findByAccountIdAndEnvironmentCrnAndClusterNameIsNull(accountId, environmentCrn)
                .orElseThrow(notFound("KerberosConfig for environment", environmentCrn));
        return krbConfig.getRealm();
    }

    private void validateSameAccount(String userAccountId, String environmentCrn) {
        String environmentCrnAccountId = Crn.safeFromString(environmentCrn).getAccountId();
        if (!environmentCrnAccountId.equals(userAccountId)) {
            throw new BadRequestException("User and Environment must be in the same account");
        }
    }

    private void validateWorkloadUserInEnvironment(String workloadUsername, String environmentCrn) {
        String accountId = Crn.safeFromString(environmentCrn).getAccountId();
        FreeIpaClient freeIpaClient;
        try {
            freeIpaClient = freeIpaClientFactory.getFreeIpaClientByAccountAndEnvironment(environmentCrn, accountId);
            Optional<User> user = freeIpaClient.userFind(workloadUsername);
            if (user.isEmpty()) {
                throw new NotFoundException(String.format("Workload user %s has not been synced into environment %s", workloadUsername, environmentCrn));
            }
        } catch (FreeIpaClientException e) {
            throw new RuntimeException(e);
        }
    }

    public String getKeytabBase64(String userCrn, String environmentCrn) {
        String userAccountId = Crn.safeFromString(userCrn).getAccountId();
        validateSameAccount(userAccountId, environmentCrn);

        String realm = getKerberosRealm(userAccountId, environmentCrn);

        GetActorWorkloadCredentialsResponse getActorWorkloadCredentialsResponse =
                grpcUmsClient.getActorWorkloadCredentials(INTERNAL_ACTOR_CRN, userCrn, MDCUtils.getRequestId());
        String workloadUsername = getActorWorkloadCredentialsResponse.getWorkloadUsername();
        validateWorkloadUserInEnvironment(workloadUsername, environmentCrn);

        List<ActorKerberosKey> actorKerberosKeys = getActorWorkloadCredentialsResponse.getKerberosKeysList();
        return userKeytabGenerator.generateKeytabBase64(workloadUsername, realm, actorKerberosKeys);
    }
}
