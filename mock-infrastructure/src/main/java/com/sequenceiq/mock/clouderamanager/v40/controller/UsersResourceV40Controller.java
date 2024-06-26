package com.sequenceiq.mock.clouderamanager.v40.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.sequenceiq.mock.clouderamanager.base.UsersResourceOperation;
import com.sequenceiq.mock.swagger.model.ApiUser2;
import com.sequenceiq.mock.swagger.model.ApiUser2List;
import com.sequenceiq.mock.swagger.v40.api.UsersResourceApi;

@Controller
public class UsersResourceV40Controller implements UsersResourceApi {

    @Inject
    private UsersResourceOperation usersResourceOperation;

    @Override
    public ResponseEntity<ApiUser2List> createUsers2(String mockUuid, @Valid ApiUser2List body) {
        return usersResourceOperation.createUsers2(mockUuid, body);
    }

    @Override
    public ResponseEntity<ApiUser2List> readUsers2(String mockUuid, @Valid String view) {
        return usersResourceOperation.readUsers2(mockUuid, view);
    }

    @Override
    public ResponseEntity<ApiUser2> updateUser2(String mockUuid, String userName, @Valid ApiUser2 body) {
        return usersResourceOperation.updateUser2(mockUuid, userName, body);
    }

    @Override
    public ResponseEntity<ApiUser2> deleteUser2(String mockUuid, String userName) {
        return usersResourceOperation.deleteUser2(mockUuid, userName);
    }
}
