package com.luo.gateway.demo1.session.defaults;

import com.luo.gateway.demo1.bind.IGenericService;
import com.luo.gateway.demo1.executor.Executor;
import com.luo.gateway.demo1.mapping.HttpStatement;
import com.luo.gateway.demo1.session.Configuration;
import com.luo.gateway.demo1.session.GateWaySession;

import java.util.Map;

public class DefaultGateWaySession implements GateWaySession {

    private Configuration configuration;
    private Executor executor;
    private String uri;

    public DefaultGateWaySession(Configuration configuration, Executor executor, String uri) {
        this.configuration = configuration;
        this.executor = executor;
        this.uri = uri;
    }

    @Override
    public Object get(String methodName, Map<String, Object> parameters) {

        HttpStatement httpStatement = configuration.getHttpStatement(uri);

        try {
            return executor.exec(httpStatement, parameters);
        } catch (Exception e) {
            throw new RuntimeException("executor error", e);
        }
    }

    @Override
    public IGenericService getMapper() {
        return configuration.getMapper(uri, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
