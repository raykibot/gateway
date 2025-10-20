package com.luo.gateway.demo1.datasource.connection;

import com.luo.gateway.demo1.datasource.Connection;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

public class DubboConnection implements Connection {

    private GenericService genericService;

    public DubboConnection(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ReferenceConfig<GenericService> referenceConfig) {

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)
                .registry(registryConfig)
                .reference(referenceConfig)
                .start();

        genericService = referenceConfig.get();
    }


    @Override
    public Object execute(String methodName, String[] parameterType, String[] parameterName, Object[] args) {
        return genericService.$invoke(methodName, parameterType, args);
    }
}
