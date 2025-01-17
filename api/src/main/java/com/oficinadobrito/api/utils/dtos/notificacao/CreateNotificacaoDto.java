package com.oficinadobrito.api.utils.dtos.notificacao;

import jakarta.validation.constraints.NotBlank;

public record CreateNotificacaoDto(
        @NotBlank(message = "usuarioId has not has by undefined")
        String usuarioId,

        @NotBlank(message = "assunto is required")
        String assunto,

        @NotBlank(message = "descricao is required")
        String descricao,

        @NotBlank(message = "status is required")
        String status
) { }
