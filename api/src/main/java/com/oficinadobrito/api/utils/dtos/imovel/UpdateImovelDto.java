package com.oficinadobrito.api.utils.dtos.imovel;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;

public record UpdateImovelDto(
        @NotBlank(message = "A Nome of imovel cannot be empty")
        String nome,

        @DecimalMin(value = "1.0", inclusive = true, message = "The length must be greater than or equal to 1.0.")
        Double comprimento,

        @DecimalMin(value = "1.0", inclusive = true, message = "The width must be greater than or equal to 1.0.")
        Double largura,

        @NotBlank(message = "The imagem of imovel cannot be empty")
        String imagem,

        @NotBlank(message = "O tipo of imovel cannot be empty")
        String tipo,

        boolean isAnunciado,

        @NotBlank(message = "A Description of imovel cannot be empty")
        String sobre,

        BigInteger anuncioId
) {
}
