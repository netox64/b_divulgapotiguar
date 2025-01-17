package com.oficinadobrito.api.utils.dtos.plano;

import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.time.LocalDate;

public record CreatePlanoDto(

        @NotNull(message = "Valor cannot be null")
        @Positive(message = "Valor must be greater than zero")
        double valor,

        @Min(value = 0, message = "quantAnuncio must be at least 0")
        @Max(value = 50, message = "quantAnuncio must be at most 120")
        int quantAnuncio,

        @NotNull(message = "dataAdquerido cannot be null")
        @FutureOrPresent(message = "dataAdquerido must be a date in the present or future")
        LocalDate dataAdquerido,

        @NotNull(message = "pagamentoId cannot be null")
        BigInteger pagamentoId,

        @NotNull(message = "usuarioId cannot be null")
        @Size(min = 36, max = 36, message = "usuarioId must be a valid UUID string")
        String usuarioId
) { }
