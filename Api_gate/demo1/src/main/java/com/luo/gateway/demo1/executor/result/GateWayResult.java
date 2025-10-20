package com.luo.gateway.demo1.executor.result;

public class GateWayResult {

    private String code;

    private String message;

    private Object data;

    public GateWayResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GateWayResult BuildSuccess(Object data){
        return new GateWayResult("0000","success",data);
    }

    public static GateWayResult BuildError(Object data){
        return new GateWayResult("0001","error",data);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
