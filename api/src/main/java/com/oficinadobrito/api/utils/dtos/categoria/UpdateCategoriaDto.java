package com.oficinadobrito.api.utils.dtos.categoria;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoriaDto(
        @NotBlank(message = "categoria name is required") String categoria
) {
}
