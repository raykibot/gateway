package com.luo.gateway.demo1.session;

public interface GateWaySessionFactory {

    GateWaySession openSession(String uri);

}
