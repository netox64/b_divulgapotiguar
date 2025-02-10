package com.oficinadobrito.api.utils.dtos.plano;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdatePlanoDto(
        String nome,
        double valor,
        int quantAnuncio,
        int duracao,
        String usuarioId
) { }
