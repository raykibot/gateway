package com.luo.gateway.demo1.session;

import com.luo.gateway.demo1.bind.IGenericService;
import com.luo.gateway.demo1.bind.RegistryMapper;
import com.luo.gateway.demo1.datasource.Connection;
import com.luo.gateway.demo1.executor.Executor;
import com.luo.gateway.demo1.executor.SimpleExecutor;
import com.luo.gateway.demo1.mapping.HttpStatement;
import com.luo.gateway.demo1.session.defaults.DefaultGateWaySession;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;


import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private final RegistryMapper registryMapper = new RegistryMapper(this);

    private final Map<String, HttpStatement> httpStatementMap = new HashMap<>();

    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public Configuration() {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("api-test-gateway");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("com.luo.gateway.demo1.IActivity");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");

        applicationConfigMap.put("api-test-gateway", applicationConfig);
        registryConfigMap.put("api-test-gateway", registryConfig);
        referenceConfigMap.put("com.luo.gateway.demo1.IActivity", referenceConfig);
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public HttpStatement getHttpStatement(String uri) {
        return httpStatementMap.get(uri);
    }

    public Executor newInstance(Connection connection) {
        return new SimpleExecutor(this, connection);
    }

    public IGenericService getMapper(String uri, DefaultGateWaySession defaultGateWaySession) {
        return registryMapper.getMapper(uri, defaultGateWaySession);
    }
}
