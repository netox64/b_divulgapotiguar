package com.oficinadobrito.api.utils.dtos.plano;

import jakarta.validation.constraints.*;

public record UpdatePlanoDto(
        @Positive(message = "Valor must be greater than zero")
        double valor,

        @Min(value = 0, message = "quantAnuncio must be at least 0")
        @Max(value = 120, message = "quantAnuncio must be at most 120")
        int quantAnuncio,

        @NotNull(message = "usuarioId cannot be null")
        @Size(min = 36, max = 36, message = "usuarioId must be a valid UUID string")
        String usuarioId
) { }
