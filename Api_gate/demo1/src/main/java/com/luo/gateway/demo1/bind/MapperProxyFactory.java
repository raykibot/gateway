package com.luo.gateway.demo1.bind;

import com.luo.gateway.demo1.mapping.HttpStatement;
import com.luo.gateway.demo1.session.GateWaySession;
import com.luo.gateway.demo1.session.defaults.DefaultGateWaySession;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperProxyFactory {


    private String uri;

    public MapperProxyFactory(String uri) {
        this.uri = uri;
    }

    private final Map<String, IGenericService> genericServiceCacheMap = new ConcurrentHashMap<>();

    public IGenericService newInstance(GateWaySession gateWaySession) {

        return genericServiceCacheMap.computeIfAbsent(uri, k ->{
            HttpStatement httpStatement = gateWaySession.getConfiguration().getHttpStatement(uri);
            MapperProxy mapperProxy = new MapperProxy(gateWaySession,uri);

            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(httpStatement.getMethodName(), Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = interfaceMaker.create();

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{IGenericService.class, interfaceClass});
            enhancer.setCallback(mapperProxy);
            return (IGenericService) enhancer.create();
        });
    }
}
