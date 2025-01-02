package com.oficinadobrito.api.utils.dtos.imovel;

import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;

public record UpdateImovelDto(
        @NotBlank(message = "A Nome of imovel cannot be empty")
        String nome,

        @NotBlank(message = "A Area of imovel cannot be empty")
        String area,

        @NotBlank(message = "O tipo of imovel cannot be empty")
        String tipo,

        @NotBlank(message = "A Description of imovel cannot be empty")
        String sobre,

        BigInteger anuncioId
) {
}
