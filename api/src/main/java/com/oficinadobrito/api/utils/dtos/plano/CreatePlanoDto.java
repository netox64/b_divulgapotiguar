package com.oficinadobrito.api.utils.dtos.plano;

import jakarta.validation.constraints.*;

public record CreatePlanoDto(
        @NotBlank(message = "Nome is required")
        String nome,
        
        @NotNull(message = "Valor cannot be null")
        @Positive(message = "Valor must be greater than zero")
        double valor,

        @Min(value = 0, message = "quantAnuncio must be at least 0")
        @Max(value = 50, message = "quantAnuncio must be at most 120")
        int quantAnuncio,

        @Min(value = 0, message = "duracao must be at least 0")
        @Max(value = 356, message = "duracao must be at most 365")
        int duracao,

        @NotNull(message = "adquirido cannot be null")
        boolean adquirido,

        @NotNull(message = "usuarioId cannot be null")
        @Size(min = 36, max = 36, message = "usuarioId must be a valid ")
        String usuarioId
) { }
