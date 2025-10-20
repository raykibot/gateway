package com.luo.gateway.demo1.bind;

import com.luo.gateway.demo1.session.GateWaySession;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy implements MethodInterceptor {

    private GateWaySession gateWaySession;
    private String uri;

    public MapperProxy(GateWaySession gateWaySession, String uri) {
        this.gateWaySession = gateWaySession;
        this.uri = uri;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        MapperMethod mapperMethod = new MapperMethod(method, uri,gateWaySession.getConfiguration());
        return mapperMethod.execute(gateWaySession, (Map<String, Object>) objects[0]);
    }
}
