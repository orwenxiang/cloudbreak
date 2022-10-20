package com.sequenceiq.freeipa.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.sequenceiq.authorization.annotation.FilterListBasedOnPermissions;
import com.sequenceiq.authorization.resource.AuthorizationResourceAction;
import com.sequenceiq.cloudbreak.auth.ThreadBasedUserCrnProvider;
import com.sequenceiq.cloudbreak.auth.altus.EntitlementService;
import com.sequenceiq.cloudbreak.common.cost.RealTimeCostResponse;
import com.sequenceiq.cloudbreak.cost.CostCalculationNotEnabledException;
import com.sequenceiq.cloudbreak.structuredevent.rest.annotation.AccountEntityType;
import com.sequenceiq.freeipa.api.v1.cost.FreeIpaCostV1Endpoint;
import com.sequenceiq.freeipa.api.v1.freeipa.stack.model.list.ListFreeIpaResponse;
import com.sequenceiq.freeipa.authorization.FreeIpaFiltering;
import com.sequenceiq.freeipa.cost.FreeIpaCostService;
import com.sequenceiq.freeipa.entity.Stack;

@Controller
@AccountEntityType(Stack.class)
public class FreeIpaCostV1Controller implements FreeIpaCostV1Endpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeIpaCostV1Controller.class);

    @Inject
    private FreeIpaFiltering freeIpaFiltering;

    @Inject
    private FreeIpaCostService freeIpaCostService;

    @Inject
    private EntitlementService entitlementService;

    @Override
    @FilterListBasedOnPermissions
    public RealTimeCostResponse list() {
        checkIfCostCalculationIsEnabled();
        List<ListFreeIpaResponse> freeIpas = freeIpaFiltering.filterFreeIpas(AuthorizationResourceAction.DESCRIBE_ENVIRONMENT);
        return new RealTimeCostResponse(freeIpaCostService.getCosts(freeIpas));
    }

    private void checkIfCostCalculationIsEnabled() {
        if (!entitlementService.isCostCalculationEnabled(ThreadBasedUserCrnProvider.getAccountId())) {
            LOGGER.info("Cost calculation feature is not enabled!");
            throw new CostCalculationNotEnabledException("Cost calculation feature is not enabled!");
        }
    }
}
