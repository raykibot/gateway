package com.luo.gateway.demo1.bind;

import com.luo.gateway.demo1.session.Configuration;
import com.luo.gateway.demo1.session.defaults.DefaultGateWaySession;

import java.util.HashMap;
import java.util.Map;

public class RegistryMapper {

    private final Configuration configuration;

    public RegistryMapper(Configuration configuration) {
        this.configuration = configuration;
    }

    private final Map<String, MapperProxyFactory> proxyFactoryMap = new HashMap<>();

    public IGenericService getMapper(String uri, DefaultGateWaySession defaultGateWaySession) {
        final MapperProxyFactory factory = proxyFactoryMap.get(uri);
        if (factory == null) throw new RuntimeException("No MapperProxyFactory for " + uri);
        return factory.newInstance(defaultGateWaySession);
    }
}
