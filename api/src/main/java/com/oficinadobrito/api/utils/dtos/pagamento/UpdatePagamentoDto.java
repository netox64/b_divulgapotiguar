package com.oficinadobrito.api.utils.dtos.pagamento;

import java.math.BigInteger;

public record UpdatePagamentoDto(
        String tipo,
        String cartao,
        String chavePix,
        String usuarioId,
        BigInteger[] planosIds
) { }
