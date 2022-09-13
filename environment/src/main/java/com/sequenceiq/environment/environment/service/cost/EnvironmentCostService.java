package com.sequenceiq.environment.environment.service.cost;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.api.endpoint.v4.cost.ClusterCostV4Endpoint;
import com.sequenceiq.cloudbreak.common.cost.RealTimeCost;
import com.sequenceiq.environment.environment.dto.EnvironmentDto;

@Service
public class EnvironmentCostService {

    @Inject
    private ClusterCostV4Endpoint clusterCostV4Endpoint;

    public List<RealTimeCost> getCosts(List<EnvironmentDto> environmentDtos) {
        Map<String, RealTimeCost> map = new HashMap<>();
        List<RealTimeCost> dhCosts = clusterCostV4Endpoint.list().getCost();
        for (RealTimeCost dhCost : dhCosts) {
            String envCrn = dhCost.getEnvCrn();
            RealTimeCost cost = map.getOrDefault(envCrn, new RealTimeCost(envCrn, envCrn, 0.0, 0.0, 0.0));
            map.put(envCrn, cost.add(dhCost));
        }
        return map.values().stream().collect(toList());
    }
}
