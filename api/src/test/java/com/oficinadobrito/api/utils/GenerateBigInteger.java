package com.oficinadobrito.api.utils;

import java.math.BigInteger;
import java.util.Random;

public class GenerateBigInteger {

    public static BigInteger generateCustomRandom(){
        Random random = new Random();
        return new BigInteger(32, random);
    }
}
