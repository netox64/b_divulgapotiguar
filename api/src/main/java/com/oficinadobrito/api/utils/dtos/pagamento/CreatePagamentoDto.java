package com.oficinadobrito.api.utils.dtos.pagamento;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;


public record CreatePagamentoDto(
        @NotBlank(message = "tipo has required")
        String tipo,

        String cartao,

        String chavePix,

        @Min(value = 0, message = "The value not has negative")
        double valor,

        @NotBlank(message = "A id for Usuario has required")
        String usuarioId,

        BigInteger[] planosIds
) { }
