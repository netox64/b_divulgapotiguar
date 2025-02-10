package com.oficinadobrito.api.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ANUNCIADO, ANALISADO, PENDENTE, INVALIDO;

    @JsonCreator
    public static Status fromString(String value) {
        if (value != null) {
            return switch (value.toUpperCase()) {
                case "ANUNCIADO" -> ANUNCIADO;
                case "ANALISADO" -> ANALISADO;
                case "PENDENTE" -> PENDENTE;
                case "INVALIDO" -> INVALIDO;
                default -> throw new IllegalArgumentException("Unknown status: " + value);
            };
        }
        throw new IllegalArgumentException("Status cannot be null");
    }
}
