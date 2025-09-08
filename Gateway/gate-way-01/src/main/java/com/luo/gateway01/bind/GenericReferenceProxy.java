package com.luo.gateway01.bind;


import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class GenericReferenceProxy implements MethodInterceptor {

    /**
     * 泛化调用服务RPC
     */
    private final GenericService genericService;

    /**
     * 泛化调用方法 RPC
     */
    private final String methodName;

    public GenericReferenceProxy(GenericService genericService, String methodName) {
        this.genericService = genericService;
        this.methodName = methodName;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameters = new String[parameterTypes.length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameterTypes[i].getName();
        }

        // 举例：genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"world"});
        return genericService.$invoke(methodName,parameters,args);
    }
}
