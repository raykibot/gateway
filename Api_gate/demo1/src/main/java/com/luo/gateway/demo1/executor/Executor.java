package com.luo.gateway.demo1.executor;

import com.luo.gateway.demo1.executor.result.GateWayResult;
import com.luo.gateway.demo1.mapping.HttpStatement;

import java.util.Map;

public interface Executor {



    GateWayResult exec(HttpStatement httpStatement, Map<String, Object> params);




}
