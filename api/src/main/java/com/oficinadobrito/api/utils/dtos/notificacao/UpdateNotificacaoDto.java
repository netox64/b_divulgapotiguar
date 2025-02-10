package com.oficinadobrito.api.utils.dtos.notificacao;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateNotificacaoDto(
        String assunto,
        String descricao,
        String status
) { }
