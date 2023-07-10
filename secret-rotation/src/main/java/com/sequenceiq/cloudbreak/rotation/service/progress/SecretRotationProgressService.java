package com.sequenceiq.cloudbreak.rotation.service.progress;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.rotation.RotationFlowExecutionType;
import com.sequenceiq.cloudbreak.rotation.SecretRotationStep;
import com.sequenceiq.cloudbreak.rotation.SecretType;
import com.sequenceiq.cloudbreak.rotation.entity.SecretRotationStepProgress;
import com.sequenceiq.cloudbreak.rotation.repository.SecretRotationStepProgressRepository;

@Service
public class SecretRotationProgressService {

    @Inject
    private SecretRotationStepProgressRepository repository;

    public void finished(SecretRotationStepProgress entity) {
        entity.setFinished(System.currentTimeMillis());
        repository.save(entity);
    }

    public Optional<SecretRotationStepProgress> latestStep(String resourceCrn, SecretType secretType,
            SecretRotationStep step, RotationFlowExecutionType executionType) {
        Optional<SecretRotationStepProgress> latestStepProgress = repository.findAllByResourceCrnAndExecutionType(resourceCrn, executionType)
                .stream()
                .filter(progress -> secretType.equals(progress.getSecretType()) && step.equals(progress.getSecretRotationStep()))
                .findFirst();
        if (latestStepProgress.isEmpty()) {
            SecretRotationStepProgress progress = new SecretRotationStepProgress(resourceCrn, secretType, step, executionType, System.currentTimeMillis());
            return Optional.of(repository.save(progress));
        }
        return latestStepProgress;
    }

    public void deleteAll(String resourceCrn, SecretType secretType) {
        repository.deleteByResourceCrnAndSecretType(resourceCrn, secretType);
    }
}
