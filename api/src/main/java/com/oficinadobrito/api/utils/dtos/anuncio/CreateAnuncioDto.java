package com.oficinadobrito.api.utils.dtos.anuncio;

import jakarta.validation.constraints.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

public record CreateAnuncioDto(
        @NotBlank(message = "Description cannot be empty")
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String descricao,

        @Size(max = 255, message = "Advertising image cannot be longer than 255 characters")
        String imagemPropaganda,

        @NotBlank(message = "")
        @Size(max = 50, message = "Payment type cannot be longer than 50 characters")
        String tipoPagamento,

        @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
        double preco,

        @FutureOrPresent(message = "Announcement date must be in the present or future")
        LocalDate dataAnuncio,

        @NotNull(message = "Property cannot be null")
        BigInteger imovelId, // Usamos apenas o ID para associar com o Imóvel

        Set<BigInteger> categoriasIds // Usamos apenas os IDs das categorias
) { }
