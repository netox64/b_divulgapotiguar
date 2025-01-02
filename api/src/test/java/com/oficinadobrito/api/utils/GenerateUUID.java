package com.oficinadobrito.api.utils;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class GenerateUUID {

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
