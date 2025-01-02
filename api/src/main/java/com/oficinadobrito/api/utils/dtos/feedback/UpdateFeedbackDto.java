package com.oficinadobrito.api.utils.dtos.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record UpdateFeedbackDto(
        @NotNull(message = "The user who is updating must be informed")
        String usuarioRequestId,

        @NotNull(message = "username of sender cannot be null")
        String remetenteUsername,

        @Min(value = 0, message = "Stars cannot be less than 0")
        @Max(value = 5, message = "Stars cannot be greater than 5")
        double stars,

        String comentario,


        String usuarioId,

        @Min(value = 1, message = "AnuncioId must be a positive number")
        BigInteger anuncioId
) { }
