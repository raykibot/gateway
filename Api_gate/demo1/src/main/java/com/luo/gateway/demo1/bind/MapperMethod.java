package com.luo.gateway.demo1.bind;

import com.luo.gateway.demo1.mapping.HttpCommandType;
import com.luo.gateway.demo1.session.Configuration;
import com.luo.gateway.demo1.session.GateWaySession;

import java.lang.reflect.Method;
import java.util.Map;

public class MapperMethod {

    private String methodName;

    private HttpCommandType httpCommandType;

    public MapperMethod(Method method, String uri, Configuration configuration) {
        this.methodName = configuration.getHttpStatement(uri).getMethodName();
        this.httpCommandType = configuration.getHttpStatement(uri).getHttpCommandType();
    }

    public Object execute(GateWaySession gateWaySession, Map<String, Object> args) {
        Object result = null;
        switch (httpCommandType) {
            case GET:
                result = gateWaySession.get(methodName, args);
                break;
            case POST:
                result = gateWaySession.get(methodName, args);
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("unknown http command type");
        }
        return result;
    }
}
