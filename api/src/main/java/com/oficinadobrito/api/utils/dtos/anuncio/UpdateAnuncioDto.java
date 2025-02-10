package com.oficinadobrito.api.utils.dtos.anuncio;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateAnuncioDto(
        String title,
        String descricao,
        String imagemPropaganda,
        String tipoPagamento,
        Double stars,
        Double preco,
        LocalDate dataAnuncio,
        BigInteger imovelId,
        Set<BigInteger> categoriasIds
) { }
