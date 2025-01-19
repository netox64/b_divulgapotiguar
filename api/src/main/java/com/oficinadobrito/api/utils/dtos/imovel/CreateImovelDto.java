package com.oficinadobrito.api.utils.dtos.imovel;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateImovelDto(
        @NotBlank(message = "A Nome of imovel cannot be empty")
        String nome,

        @NotBlank(message = "A localização of imovel cannot be empty")
        String localizacao,

        @NotNull(message = "Length is mandatory.")
        @DecimalMin(value = "1.0", inclusive = true, message = "The length must be greater than or equal to 1.0.")
        Double comprimento,

        @NotNull(message = "Length is mandatory.")
        @DecimalMin(value = "1.0", inclusive = true, message = "The width must be greater than or equal to 1.0.")
        Double largura,

        @NotBlank(message = "The imagem of imovel cannot be empty")
        String imagem,

        @NotBlank(message = "O tipo of imovel cannot be empty")
        String tipo,

        @NotBlank(message = "A Description of imovel cannot be empty")
        String sobre,

        @NotBlank(message = "A usuario of imovel cannot be empty")
        String usuarioId
) {
        public Double areaCalculada() {
                return comprimento * largura;
        }
}
