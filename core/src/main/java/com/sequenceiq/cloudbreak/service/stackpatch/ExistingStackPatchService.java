package com.sequenceiq.cloudbreak.service.stackpatch;

import static com.sequenceiq.cloudbreak.util.Benchmark.checkedMeasure;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.StackPatch;
import com.sequenceiq.cloudbreak.domain.stack.StackPatchType;
import com.sequenceiq.cloudbreak.repository.StackPatchRepository;
import com.sequenceiq.flow.core.FlowLogService;
import com.sequenceiq.flow.domain.FlowLog;
import com.sequenceiq.flow.service.FlowRetryService;

public abstract class ExistingStackPatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExistingStackPatchService.class);

    @Inject
    private StackPatchRepository stackPatchRepository;

    @Inject
    private FlowLogService flowLogService;

    @Inject
    private FlowRetryService flowRetryService;

    public boolean isStackAlreadyFixed(Stack stack) {
        return stackPatchRepository.findByStackAndType(stack, getStackPatchType()).isPresent();
    }

    /**
     * @param stack the stack to apply the patch for
     * @return whether the patch was applied successfully
     * @throws ExistingStackPatchApplyException when something unexpected goes wrong while applying the patch
     */
    public boolean apply(Stack stack) throws ExistingStackPatchApplyException {
        if (flowLogService.isOtherFlowRunning(stack.getId())) {
            LOGGER.info("Another flow is running for stack {}, skipping patch apply to let the flow finish", stack.getResourceCrn());
            return false;
        } else {
            Optional<FlowLog> lastRetryableFailedFlow = flowRetryService.getLastRetryableFailedFlow(stack.getId());
            if (lastRetryableFailedFlow.isEmpty()) {
                try {
                    LOGGER.info("Starting stack {} patching for {}", stack.getResourceCrn(), getStackPatchType());
                    boolean success = checkedMeasure(() -> doApply(stack), LOGGER, "Existing stack patching took {} ms for stack {} and patch {}.",
                            stack.getResourceCrn(), getStackPatchType());
                    if (success) {
                        LOGGER.info("Stack {} was patched successfully for {}", stack.getResourceCrn(), getStackPatchType());
                        stackPatchRepository.save(new StackPatch(stack, getStackPatchType()));
                    } else {
                        LOGGER.info("Stack {} was not patched for {}", stack.getResourceCrn(), getStackPatchType());
                    }
                    return success;
                } catch (ExistingStackPatchApplyException e) {
                    throw e;
                } catch (Exception e) {
                    String message = String.format("Something unexpected went wrong with stack %s while applying patch %s",
                            stack.getResourceCrn(), getStackPatchType());
                    throw new ExistingStackPatchApplyException(message, e);
                }
            } else {
                LOGGER.info("Stack {} has a retryable failed flow, skipping patch apply to preserve possible retry", stack.getResourceCrn());
                return false;
            }
        }
    }

    protected boolean isCmServerReachable(Stack stack) throws ExistingStackPatchApplyException {
        return stack.getClusterManagerServer()
                .orElseThrow(() -> new ExistingStackPatchApplyException("Could not find CM server for stack: " + stack.getResourceCrn()))
                .isReachable();
    }

    protected boolean isPrimaryGatewayReachable(Stack stack) throws ExistingStackPatchApplyException {
        return Optional.ofNullable(stack.getPrimaryGatewayInstance())
                .orElseThrow(() -> new ExistingStackPatchApplyException("Could not find Primary gateway for stack: " + stack.getResourceCrn()))
                .isReachable();
    }

    /**
     * @return The StackPatchType that is patched by running the service implementation's doApply
     */
    public abstract StackPatchType getStackPatchType();

    /**
     * @return Is the stack affected by the {@link StackPatchType}
     */
    public abstract boolean isAffected(Stack stack);

    /**
     * Apply the fix of {@link StackPatchType} for the affected stack
     * @param stack the stack to apply the patch for
     * @return whether the patch was applied successful
     * @throws ExistingStackPatchApplyException when something unexpected goes wrong while applying the patch
     */
    abstract boolean doApply(Stack stack) throws ExistingStackPatchApplyException;
}
