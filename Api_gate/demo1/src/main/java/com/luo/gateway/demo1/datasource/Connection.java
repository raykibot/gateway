package com.luo.gateway.demo1.datasource;

public interface Connection {

    Object execute(String methodName, String[] parameterType, String[] parameterName, Object[] args) ;

}
