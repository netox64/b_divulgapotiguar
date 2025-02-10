package com.oficinadobrito.api.utils.dtos.imovel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oficinadobrito.api.utils.enums.Status;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateImovelDto(
    String nome,
    String localizacao,
    Double comprimento,
    Double largura,
    String imagemImovel,
    String tipo,
    String matriculaCartorio,
    String nomeProprietario,
    String cpfCnpjProprietario,
    String rgProprietario,
    String restricoes,
    String nomeCartorioRegistrado,
    String assinaturaControle,
    Status status,
    Boolean isAnunciado,
    String sobre,
    BigInteger anuncioId
) {
}
