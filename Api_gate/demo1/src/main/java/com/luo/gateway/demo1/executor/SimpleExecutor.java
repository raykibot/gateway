package com.luo.gateway.demo1.executor;

import com.luo.gateway.demo1.datasource.Connection;
import com.luo.gateway.demo1.session.Configuration;

public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Connection connection) {
        super(configuration, connection);
    }

    @Override
    protected Object doExec(String methodName, String[] parameterTypeTypes, Object[] args) {
        return connection.execute(methodName, parameterTypeTypes, new String[]{"ignore"}, args);
    }
}
