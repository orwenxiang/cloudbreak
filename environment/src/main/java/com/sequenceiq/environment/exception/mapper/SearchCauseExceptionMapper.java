package com.sequenceiq.environment.exception.mapper;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Providers;

import org.apache.commons.lang3.tuple.Pair;

import com.sequenceiq.cloudbreak.exception.mapper.BaseExceptionMapper;

public abstract class SearchCauseExceptionMapper<T extends Throwable> extends EnvironmentBaseExceptionMapper<T> {

    @Context
    private Providers providers;

    @Override
    public Response.StatusType getResponseStatus(T exception) {
        Response.StatusType defaultResponse = Response.Status.BAD_REQUEST;
        if (exception != null) {
            Pair<BaseExceptionMapper, ? extends Throwable> pair = searchRecursively(exception);
            if (pair.getKey() != null && pair.getKey() != this && !(pair.getKey() instanceof DefaultEnvironmentExceptionMapper)) {
                defaultResponse = pair.getKey().getResponseStatus(pair.getValue());
            }
        }
        return defaultResponse;
    }

    private Pair<BaseExceptionMapper, Throwable> searchRecursively(Throwable exception) {
        if (exception.getCause() == null) {
            BaseExceptionMapper exceptionMapper = getExceptionMapper(exception);
            return Pair.of(exceptionMapper, exception);
        }
        BaseExceptionMapper<? extends Throwable> exceptionMapper = getExceptionMapper(exception.getCause());
        if (exceptionMapper == null || exceptionMapper instanceof DefaultEnvironmentExceptionMapper) {
            return searchRecursively(exception.getCause());
        }
        return Pair.of(exceptionMapper, exception.getCause());
    }

    private BaseExceptionMapper<? extends Throwable> getExceptionMapper(Throwable exception) {
        return (BaseExceptionMapper<? extends Throwable>) providers.getExceptionMapper(exception.getClass());
    }
}
