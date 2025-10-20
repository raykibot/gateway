package com.luo.gateway.demo1.executor;

import com.luo.gateway.demo1.datasource.Connection;
import com.luo.gateway.demo1.executor.result.GateWayResult;
import com.luo.gateway.demo1.mapping.HttpStatement;
import com.luo.gateway.demo1.session.Configuration;
import com.luo.gateway.demo1.type.SimpleTypeRegistry;

import java.util.Map;

public abstract class BaseExecutor implements Executor{

    protected Configuration configuration;
    protected Connection connection;

    public BaseExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.connection = connection;
    }

    @Override
    public GateWayResult exec(HttpStatement httpStatement, Map<String, Object> params) {

        String methodName = httpStatement.getMethodName();
        String parameterType = httpStatement.getParameterType();
        String[] parameterTypes = new String[]{parameterType};
        Object[] args = SimpleTypeRegistry.isSimpleType(parameterType) ? params.values().toArray() : new Object[]{params};

        try {
            Object result = doExec(methodName, parameterTypes, args);
            return GateWayResult.BuildSuccess(result);
        } catch (Exception e) {
            return GateWayResult.BuildError(e.getMessage());
        }
    }

    protected abstract Object doExec(String methodName, String[] parameterTypeTypes, Object[] args);

}
