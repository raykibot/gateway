package com.luo.gateway.demo1.session;

import com.luo.gateway.demo1.bind.IGenericService;

import java.util.Map;

public interface GateWaySession {


    Object get(String methodName, Map<String, Object> parameters);

    IGenericService getMapper();

    Configuration getConfiguration();


}
