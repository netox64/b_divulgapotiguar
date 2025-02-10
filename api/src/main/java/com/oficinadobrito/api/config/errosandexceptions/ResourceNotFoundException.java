package com.oficinadobrito.api.config.errosandexceptions;

import com.oficinadobrito.api.config.errosandexceptions.base.CustomExceptions;

public class ResourceNotFoundException extends CustomExceptions {

    //Descrição: Um erro genérico do servidor que ocorre quando uma exceção inesperada ou falha interna ocorre.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

