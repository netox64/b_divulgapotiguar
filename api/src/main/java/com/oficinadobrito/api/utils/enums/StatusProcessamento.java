package com.oficinadobrito.api.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusProcessamento {
    PENDENTE, EM_PROCESSAMENTO, CONCLUIDO, ERRO;

    @JsonCreator
    public static StatusProcessamento fromString(String value) {
        if (value != null) {
            return switch (value.toUpperCase()) {
                case "CONCLUIDO" -> CONCLUIDO;
                case "EM_PROCESSAMENTO" -> EM_PROCESSAMENTO;
                case "PENDENTE" -> PENDENTE;
                case "ERRO" -> ERRO;
                default -> throw new IllegalArgumentException("Unknown status: " + value);
            };
        }
        throw new IllegalArgumentException("Status cannot be null");
    }
}
