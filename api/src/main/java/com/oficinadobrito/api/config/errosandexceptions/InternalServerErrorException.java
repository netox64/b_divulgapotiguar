package com.oficinadobrito.api.config.errosandexceptions;

import com.oficinadobrito.api.config.errosandexceptions.base.CustomExceptions;
import java.time.LocalDate;

public class InternalServerErrorException extends CustomExceptions {

    private static final String nomeApi = "Divulga Potiguar";
    private static final String space = " ";

    //Descrição: Um erro genérico do servidor que ocorre quando uma exceção inesperada ou falha interna ocorre.
    public InternalServerErrorException(String message) {
        super(nomeApi  + " \n date:"+ space + LocalDate.now() + " \n Error:" + space + message);
    }
}
