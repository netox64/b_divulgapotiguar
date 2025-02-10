package com.oficinadobrito.api.utils.dtos.anuncio;

import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.util.Set;

public record CreateAnuncioDto(
        @NotBlank(message = "Title cannot be empty")
        @Size(max = 255, message = "Title cannot be longer than 255 characters")
        String title,

        @NotBlank(message = "Description cannot be empty")
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String descricao,

        @Size(max = 255, message = "Advertising image cannot be longer than 255 characters")
        String imagemPropaganda,

        @NotBlank(message = "Pyment is required")
        @Size(max = 50, message = "Payment type cannot be longer than 50 characters")
        String tipoPagamento,

        @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
        double preco,

        @NotNull(message = "Property cannot be null")
        BigInteger imovelId,

        @NotNull(message = "Property cannot be null")
        BigInteger planoId,

        @NotNull(message = "Property cannot be null")
        String usuarioId,

        Set<BigInteger> categoriasIds
) { }
