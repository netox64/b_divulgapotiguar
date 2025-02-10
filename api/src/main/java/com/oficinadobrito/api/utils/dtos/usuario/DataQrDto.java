package com.oficinadobrito.api.utils.dtos.usuario;

import jakarta.validation.constraints.NotBlank;

public record DataQrDto(
        @NotBlank(message = "A username for usuario is required")
    String username,
        @NotBlank(message = "A cpf of usuario is required")
                        String cpf,
        @NotBlank(message = "an amount charged to the user must be passed")
                        String valorOriginal
                        ) {}
