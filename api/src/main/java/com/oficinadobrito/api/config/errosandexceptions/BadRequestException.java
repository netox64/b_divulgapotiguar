package com.oficinadobrito.api.config.errosandexceptions;

import com.oficinadobrito.api.config.errosandexceptions.base.CustomExceptions;

public class BadRequestException
    extends CustomExceptions {

    //Descrição: Um erro genérico do servidor que ocorre quando uma exceção inesperada ou falha interna ocorre.
    public BadRequestException(String message) {
            super(message);
        }
}
