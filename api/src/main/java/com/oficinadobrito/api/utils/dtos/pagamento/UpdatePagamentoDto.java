package com.oficinadobrito.api.utils.dtos.pagamento;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdatePagamentoDto(
        String tipo,
        String cartao,
        String chavePix,
        String usuarioId,
        BigInteger[] planosIds
) { }
