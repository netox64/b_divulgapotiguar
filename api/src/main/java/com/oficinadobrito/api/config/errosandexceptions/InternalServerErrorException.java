package com.oficinadobrito.api.config.errosandexceptions;

import com.oficinadobrito.api.config.errosandexceptions.base.CustomExceptions;
import java.time.LocalDate;

public class InternalServerErrorException extends CustomExceptions {

    private static final String NOME_API = "Divulga Potiguar";
    private static final String SPACE = " ";

    //Descrição: Um erro genérico do servidor que ocorre quando uma exceção inesperada ou falha interna ocorre.
    public InternalServerErrorException(String message) {
        super(NOME_API  + " \n date:"+ SPACE + LocalDate.now() + " \n Error:" + SPACE + message);
    }
}
