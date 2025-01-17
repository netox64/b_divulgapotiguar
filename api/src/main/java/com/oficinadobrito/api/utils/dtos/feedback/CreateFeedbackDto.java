package com.oficinadobrito.api.utils.dtos.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record CreateFeedbackDto(
        @NotNull(message = "Stars cannot be null")
        @Min(value = 0, message = "Stars cannot be less than 0")
        @Max(value = 5, message = "Stars cannot be greater than 5")
        double stars,

        @NotNull(message = "comentario cannot be null")
        String comentario,

        @NotNull(message = "username of sender cannot be null")
        String remetenteUsername,

        String usuarioId,

        @Min(value = 1, message = "anuncioId must be a positive number")
        BigInteger anuncioId

) { }
