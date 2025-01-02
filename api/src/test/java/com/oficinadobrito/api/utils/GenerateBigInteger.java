package com.oficinadobrito.api.utils;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Random;

@SpringBootTest
public class GenerateBigInteger {

    public static BigInteger generateCustomRandom(){
        Random random = new Random();
        return new BigInteger(32, random);
    }
}
