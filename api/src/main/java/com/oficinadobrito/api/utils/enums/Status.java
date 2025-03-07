package com.oficinadobrito.api.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ANUNCIADO, ANALISADO,SEGUIU_PARA_ANALISE, PENDENTE, INVALIDO;

    @JsonCreator
    public static Status fromString(String value) {
        if (value != null) {
            return switch (value.toUpperCase()) {
                case "ANUNCIADO" -> ANUNCIADO;
                case "PENDENTE" -> PENDENTE;
                case "SEGUIU_PARA_ANALISE" -> SEGUIU_PARA_ANALISE;
                case "ANALISADO" -> ANALISADO;
                case "INVALIDO" -> INVALIDO;
                default -> throw new IllegalArgumentException("Unknown status: " + value);
            };
        }
        throw new IllegalArgumentException("Status cannot be null");
    }
}
