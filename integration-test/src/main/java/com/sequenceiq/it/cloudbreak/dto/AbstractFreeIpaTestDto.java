package com.sequenceiq.it.cloudbreak.dto;

import static com.sequenceiq.it.cloudbreak.context.RunningParameter.emptyRunningParameter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import com.sequenceiq.it.cloudbreak.action.Action;
import com.sequenceiq.it.cloudbreak.assertion.Assertion;
import com.sequenceiq.it.cloudbreak.context.RunningParameter;
import com.sequenceiq.it.cloudbreak.context.TestContext;
import com.sequenceiq.it.cloudbreak.microservice.FreeIpaClient;
import com.sequenceiq.it.cloudbreak.util.wait.FlowUtil;

public abstract class AbstractFreeIpaTestDto<R, S, T extends CloudbreakTestDto> extends AbstractTestDto<R, S, T, FreeIpaClient> {

    private String operationId;

    @Inject
    private FlowUtil flowUtil;

    protected AbstractFreeIpaTestDto(R request, TestContext testContext) {
        super(request, testContext);
    }

    @Override
    public T when(Class<T> entityClass, Action<T, FreeIpaClient> action) {
        return getTestContext().when(entityClass, FreeIpaClient.class, action, emptyRunningParameter());
    }

    @Override
    public T when(Action<T, FreeIpaClient> action) {
        return getTestContext().when((T) this, FreeIpaClient.class, action, emptyRunningParameter());
    }

    @Override
    public T when(Class<T> entityClass, Action<T, FreeIpaClient> action, RunningParameter runningParameter) {
        return getTestContext().when(entityClass, FreeIpaClient.class, action, runningParameter);
    }

    @Override
    public T when(Action<T, FreeIpaClient> action, RunningParameter runningParameter) {
        return getTestContext().when((T) this, FreeIpaClient.class, action, runningParameter);
    }

    @Override
    public <T extends CloudbreakTestDto> T deleteGiven(Class<T> clazz, Action<T, FreeIpaClient> action, RunningParameter runningParameter) {
        getTestContext().when((T) getTestContext().given(clazz), FreeIpaClient.class, action, runningParameter);
        return getTestContext().expect((T) getTestContext().given(clazz), BadRequestException.class, runningParameter);
    }

    @Override
    public <E extends Exception> T whenException(Class<T> entityClass, Action<T, FreeIpaClient> action, Class<E> expectedException) {
        return getTestContext().whenException(entityClass, FreeIpaClient.class, action, expectedException, emptyRunningParameter());
    }

    @Override
    public <E extends Exception> T whenException(Action<T, FreeIpaClient> action, Class<E> expectedException) {
        return getTestContext().whenException((T) this, FreeIpaClient.class, action, expectedException, emptyRunningParameter());
    }

    @Override
    public <E extends Exception> T whenException(Class<T> entityClass, Action<T, FreeIpaClient> action, Class<E> expectedException,
            RunningParameter runningParameter) {
        return getTestContext().whenException(entityClass, FreeIpaClient.class, action, expectedException, runningParameter);
    }

    @Override
    public <E extends Exception> T whenException(Action<T, FreeIpaClient> action, Class<E> expectedException, RunningParameter runningParameter) {
        return getTestContext().whenException((T) this, FreeIpaClient.class, action, expectedException, runningParameter);
    }

    @Override
    public T then(Assertion<T, FreeIpaClient> assertion) {
        return then(assertion, emptyRunningParameter());
    }

    @Override
    public T then(Assertion<T, FreeIpaClient> assertion, RunningParameter runningParameter) {
        return getTestContext().then((T) this, FreeIpaClient.class, assertion, runningParameter);
    }

    @Override
    public T then(List<Assertion<T, FreeIpaClient>> assertions) {
        List<RunningParameter> runningParameters = new ArrayList<>(assertions.size());
        for (int i = 0; i < assertions.size(); i++) {
            runningParameters.add(emptyRunningParameter());
        }
        return then(assertions, runningParameters);
    }

    @Override
    public T then(List<Assertion<T, FreeIpaClient>> assertions, List<RunningParameter> runningParameters) {
        for (int i = 0; i < assertions.size() - 1; i++) {
            getTestContext().then((T) this, FreeIpaClient.class, assertions.get(i), runningParameters.get(i));
        }
        return getTestContext().then((T) this, FreeIpaClient.class, assertions.get(assertions.size() - 1), runningParameters.get(runningParameters.size() - 1));
    }

    @Override
    public <E extends Exception> T thenException(Assertion<T, FreeIpaClient> assertion, Class<E> expectedException, RunningParameter runningParameter) {
        return getTestContext().thenException((T) this, FreeIpaClient.class, assertion, expectedException, runningParameter);
    }

    public FlowUtil getFlowUtil() {
        return flowUtil;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
