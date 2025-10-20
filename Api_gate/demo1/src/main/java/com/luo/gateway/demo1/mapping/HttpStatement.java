package com.luo.gateway.demo1.mapping;

public class HttpStatement {


    private String application;

    private String interfaceName;

    private String methodName;

    private String parameterType;

    private String uri;

    private HttpCommandType httpCommandType;

    public HttpStatement(String application, String interfaceName, String methodName, String parameterType, String uri, HttpCommandType httpCommandType) {
        this.application = application;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.uri = uri;
        this.httpCommandType = httpCommandType;
    }

    public String getApplication() {
        return application;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public String getUri() {
        return uri;
    }

    public HttpCommandType getHttpCommandType() {
        return httpCommandType;
    }
}
