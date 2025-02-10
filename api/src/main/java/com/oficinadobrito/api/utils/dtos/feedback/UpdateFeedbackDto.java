package com.oficinadobrito.api.utils.dtos.feedback;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateFeedbackDto(
        String usuarioRequestId,
        String remetenteUsername,
        double stars,
        String comentario,
        String usuarioId,
        BigInteger anuncioId
) { }
