package com.oficinadobrito.api.config.errosandexceptions.base;

import java.io.Serial;

public class CustomExceptions extends RuntimeException{
    @Serial
    private static  final long serialVersionUID = 1L;

    public CustomExceptions(String message) {
        super(message);

    }

}
