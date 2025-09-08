package com.luo.gateway01;


import com.luo.gateway01.session.SessionServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTest {

    public static void main(String[] args) {
        new SessionServer().start();
    }



}
