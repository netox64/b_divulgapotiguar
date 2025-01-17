package com.oficinadobrito.api.utils.dtos.anuncio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

public record UpdateAnuncioDto(
        @Size(max = 255, message = "Title cannot be longer than 255 characters")
        String title,
        @Size(max = 255, message = "Description cannot be longer than 255 characters")
        String descricao,

        @Size(max = 255, message = "Advertising image cannot be longer than 255 characters")
        String imagemPropaganda,

        @Size(max = 50, message = "Payment type cannot be longer than 50 characters")
        String tipoPagamento,

        @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
        Double preco,

        @FutureOrPresent(message = "Announcement date must be in the present or future")
        LocalDate dataAnuncio,

        BigInteger imovelId,

        Set<BigInteger> categoriasIds
) { }
