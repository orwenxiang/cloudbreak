package com.sequenceiq.cloudbreak.cloud.azure.validator;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.cloud.azure.AzureDiskType;

@Service
public class AzurePremiumValidatorService {

    public boolean validPremiumConfiguration(String flavor) {
        return isPremiumStorageSupportedByInstance(flavor);
    }

    public boolean premiumDiskTypeConfigured(AzureDiskType diskType) {
        return AzureDiskType.PREMIUM_LOCALLY_REDUNDANT.equals(diskType);
    }

    private boolean isPremiumStorageSupportedByInstance(String flavor) {
        String segment = flavor.split("_")[1];
        String transformedSegment = segment
                .replaceAll("[0-9]", "")
                .replaceAll("-", "")
                .toLowerCase(Locale.ROOT);

        String transformedFlavor = flavor.replaceAll(segment, transformedSegment).toLowerCase(Locale.ROOT);
        String[] items = { "_ds", "_das", "_ls", "_gs", "_fs", "_es_v3", "_eis_v3" };
        return Arrays.stream(items).parallel().anyMatch(transformedFlavor::contains);
    }
}
