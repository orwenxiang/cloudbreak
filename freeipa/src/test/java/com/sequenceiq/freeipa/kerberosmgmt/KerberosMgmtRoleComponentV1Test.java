package com.sequenceiq.freeipa.kerberosmgmt;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.googlecode.jsonrpc4j.JsonRpcClientException;
import com.sequenceiq.freeipa.api.v1.kerberosmgmt.model.RoleRequest;
import com.sequenceiq.freeipa.client.FreeIpaClient;
import com.sequenceiq.freeipa.client.FreeIpaClientException;
import com.sequenceiq.freeipa.client.FreeIpaErrorCodes;
import com.sequenceiq.freeipa.client.model.Host;
import com.sequenceiq.freeipa.client.model.Privilege;
import com.sequenceiq.freeipa.client.model.Role;
import com.sequenceiq.freeipa.client.model.Service;
import com.sequenceiq.freeipa.kerberosmgmt.v1.KerberosMgmtRoleComponent;

@ExtendWith(MockitoExtension.class)
public class KerberosMgmtRoleComponentV1Test {
    private static final String HOST = "host1";

    private static final String ROLE = "role1";

    private static final String SERVICE = "service1";

    private static final String PRIVILEGE1 = "privilege1";

    private static final String PRIVILEGE2 = "privilege2";

    private static final String ERROR_MESSAGE = "error message";

    private static final int NOT_FOUND = 4001;

    @Mock
    private FreeIpaClient mockIpaClient;

    private KerberosMgmtRoleComponent underTest;

    @BeforeEach
    public void init() {
        underTest = new KerberosMgmtRoleComponent();
    }

    @Test
    public void testAddRoleAndPrivilegesForHostWithoutRole() throws Exception {
        Host host = new Host();
        host.setFqdn(HOST);
        RoleRequest roleRequest = null;
        underTest.addRoleAndPrivileges(Optional.empty(), Optional.of(host), roleRequest, mockIpaClient);
        Mockito.verifyNoInteractions(mockIpaClient);
    }

    @Test
    public void testAddRoleAndPrivilegesForHostWithRole() throws Exception {
        Host host = new Host();
        host.setFqdn(HOST);
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Role role = new Role();
        role.setCn(ROLE);
        Mockito.when(mockIpaClient.addRole(anyString())).thenReturn(role);
        Privilege privilege = new Privilege();
        Set<String> hosts = new HashSet<>();
        hosts.add(HOST);
        Set<String> noServices = new HashSet<>();
        Mockito.when(mockIpaClient.showRole(roleRequest.getRoleName()))
                .thenThrow(new FreeIpaClientException("notfound", new JsonRpcClientException(NOT_FOUND, "notfound", null)))
                .thenReturn(role);
        Mockito.when(mockIpaClient.showPrivilege(any())).thenReturn(privilege);
        Mockito.when(mockIpaClient.addRolePrivileges(any(), any())).thenReturn(role);
        Mockito.when(mockIpaClient.addRoleMember(any(), any(), any(), any(), any(), any())).thenReturn(role);

        underTest.addRoleAndPrivileges(Optional.empty(), Optional.of(host), roleRequest, mockIpaClient);

        Mockito.verify(mockIpaClient).addRole(ROLE);
        Mockito.verify(mockIpaClient).addRolePrivileges(ROLE, privileges);
        Mockito.verify(mockIpaClient).addRoleMember(ROLE, null, null, hosts, null, noServices);
    }

    @Test
    public void testAddRoleAndPrivilegesForHostWithRoleThatAlreadyExists() throws Exception {
        Host host = new Host();
        host.setFqdn(HOST);
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Role role = new Role();
        role.setCn(ROLE);
        Privilege privilege = new Privilege();
        Set<String> hosts = new HashSet<>();
        hosts.add(HOST);
        Set<String> noServices = new HashSet<>();
        Mockito.when(mockIpaClient.showPrivilege(any())).thenReturn(privilege);
        Mockito.when(mockIpaClient.addRolePrivileges(any(), any())).thenReturn(role);
        Mockito.when(mockIpaClient.showRole(anyString())).thenReturn(role);
        Mockito.when(mockIpaClient.addRoleMember(any(), any(), any(), any(), any(), any())).thenReturn(role);

        underTest.addRoleAndPrivileges(Optional.empty(), Optional.of(host), roleRequest, mockIpaClient);

        Mockito.verify(mockIpaClient).addRolePrivileges(ROLE, privileges);
        Mockito.verify(mockIpaClient).addRoleMember(ROLE, null, null, hosts, null, noServices);
    }

    @Test
    public void testAddRoleAndPrivilegesForHostWithRoleRaceCondition() throws Exception {
        Host host = new Host();
        host.setFqdn(HOST);
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Role role = new Role();
        role.setCn(ROLE);
        Mockito.when(mockIpaClient.addRole(anyString())).thenThrow(new FreeIpaClientException("duplicate",
                new JsonRpcClientException(FreeIpaErrorCodes.DUPLICATE_ENTRY.getValue(), "duplicate", null)));
        Privilege privilege = new Privilege();
        Set<String> hosts = new HashSet<>();
        hosts.add(HOST);
        Set<String> noServices = new HashSet<>();
        Mockito.when(mockIpaClient.showRole(roleRequest.getRoleName()))
                .thenThrow(new FreeIpaClientException("notfound", new JsonRpcClientException(NOT_FOUND, "notfound", null)))
                .thenReturn(role);
        Mockito.when(mockIpaClient.showPrivilege(any())).thenReturn(privilege);
        Mockito.when(mockIpaClient.addRolePrivileges(any(), any())).thenReturn(role);
        Mockito.when(mockIpaClient.addRoleMember(any(), any(), any(), any(), any(), any())).thenReturn(role);

        underTest.addRoleAndPrivileges(Optional.empty(), Optional.of(host), roleRequest, mockIpaClient);

        Mockito.verify(mockIpaClient).addRole(ROLE);
        Mockito.verify(mockIpaClient).addRolePrivileges(ROLE, privileges);
        Mockito.verify(mockIpaClient).addRoleMember(ROLE, null, null, hosts, null, noServices);
    }

    @Test
    public void testAddRoleAndPrivilegesForHostWithException() throws Exception {
        Host host = new Host();
        host.setFqdn(HOST);
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Role role = new Role();
        role.setCn(ROLE);
        Mockito.when(mockIpaClient.addRole(anyString())).thenThrow(new FreeIpaClientException("expected"));
        Mockito.when(mockIpaClient.showRole(roleRequest.getRoleName()))
                .thenThrow(new FreeIpaClientException("notfound", new JsonRpcClientException(NOT_FOUND, "notfound", null)))
                .thenReturn(role);

        assertThrows(FreeIpaClientException.class,
                () -> underTest.addRoleAndPrivileges(Optional.empty(), Optional.of(host), roleRequest, mockIpaClient));
    }

    @Test
    public void testAddRoleAndPrivilegesForServiceWithoutRole() throws Exception {
        Service service = new Service();
        service.setKrbprincipalname(List.of(SERVICE));

        underTest.addRoleAndPrivileges(Optional.of(service), Optional.empty(), null, mockIpaClient);

        Mockito.verifyNoInteractions(mockIpaClient);
    }

    @Test
    public void testAddRoleAndPrivilegesForServiceWithRole() throws Exception {
        Service service = new Service();
        service.setKrbprincipalname(List.of(SERVICE));
        service.setKrbcanonicalname(SERVICE);
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Role role = new Role();
        role.setCn(ROLE);
        Mockito.when(mockIpaClient.addRole(anyString())).thenReturn(role);
        Privilege privilege = new Privilege();
        Set<String> noHosts = new HashSet<>();
        Set<String> services = new HashSet<>();
        services.add(SERVICE);
        Mockito.when(mockIpaClient.showRole(roleRequest.getRoleName()))
                .thenThrow(new FreeIpaClientException("notfound", new JsonRpcClientException(NOT_FOUND, "notfound", null)))
                .thenReturn(role);
        Mockito.when(mockIpaClient.showPrivilege(any())).thenReturn(privilege);
        Mockito.when(mockIpaClient.addRolePrivileges(any(), any())).thenReturn(role);
        Mockito.when(mockIpaClient.addRoleMember(any(), any(), any(), any(), any(), any())).thenReturn(role);

        underTest.addRoleAndPrivileges(Optional.of(service), Optional.empty(), roleRequest, mockIpaClient);

        Mockito.verify(mockIpaClient).addRole(ROLE);
        Mockito.verify(mockIpaClient).addRolePrivileges(ROLE, privileges);
        Mockito.verify(mockIpaClient).addRoleMember(ROLE, null, null, noHosts, null, services);
    }

    @Test
    public void testDeleteRoleIfNoLongerUsedWhenRoleIsNull() throws Exception {
        underTest.deleteRoleIfItIsNoLongerUsed(null, mockIpaClient);

        Mockito.verifyNoInteractions(mockIpaClient);
    }

    @Test
    public void testDeleteRoleIfNoLongerUsedWhenRoleDoesNotExist() throws Exception {
        Mockito.when(mockIpaClient.showRole(anyString())).thenThrow(new FreeIpaClientException(ERROR_MESSAGE,
                new JsonRpcClientException(NOT_FOUND, ERROR_MESSAGE, null)));

        underTest.deleteRoleIfItIsNoLongerUsed(ROLE, mockIpaClient);

        Mockito.verify(mockIpaClient, Mockito.never()).deleteRole(ROLE);
    }

    @Test
    public void testDeleteRoleIfNoLongerUsedWhenRoleIsStillUsedAsMemberHost() throws Exception {
        Role role = new Role();
        role.setCn(ROLE);
        List<String> hosts = new ArrayList<>();
        hosts.add(HOST);
        role.setMemberHost(hosts);
        Mockito.when(mockIpaClient.showRole(anyString())).thenReturn(role);

        underTest.deleteRoleIfItIsNoLongerUsed(ROLE, mockIpaClient);

        Mockito.verify(mockIpaClient, Mockito.never()).deleteRole(ROLE);
    }

    @Test
    public void testDeleteRoleIfNoLongerUsedWhenRoleIsStillUsedAsMemberService() throws Exception {
        Role role = new Role();
        role.setCn(ROLE);
        List<String> services = new ArrayList<>();
        services.add(SERVICE);
        role.setMemberService(services);
        Mockito.when(mockIpaClient.showRole(anyString())).thenReturn(role);

        underTest.deleteRoleIfItIsNoLongerUsed(ROLE, mockIpaClient);

        Mockito.verify(mockIpaClient, Mockito.never()).deleteRole(ROLE);
    }

    @Test
    public void testDeleteRoleIfNoLongerUsedWhenRoleIsNotUsed() throws Exception {
        Role role = new Role();
        role.setCn(ROLE);
        Mockito.when(mockIpaClient.showRole(anyString())).thenReturn(role);

        underTest.deleteRoleIfItIsNoLongerUsed(ROLE, mockIpaClient);

        Mockito.verify(mockIpaClient).deleteRole(ROLE);
    }

    @Test
    public void testPrivilegeExistReturnTrue() throws Exception {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Privilege privilege = new Privilege();
        Mockito.when(mockIpaClient.showPrivilege(any())).thenReturn(privilege);

        Assertions.assertTrue(underTest.privilegesExist(roleRequest, mockIpaClient));
    }

    @Test
    public void testPrivilegeExistReturnFalse() throws Exception {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleName(ROLE);
        Set<String> privileges = new HashSet<>();
        privileges.add(PRIVILEGE1);
        privileges.add(PRIVILEGE2);
        roleRequest.setPrivileges(privileges);
        Mockito.when(mockIpaClient.showPrivilege(any())).thenThrow(new FreeIpaClientException(ERROR_MESSAGE,
                new JsonRpcClientException(NOT_FOUND, ERROR_MESSAGE, null)));

        Assertions.assertFalse(underTest.privilegesExist(roleRequest, mockIpaClient));
    }
}