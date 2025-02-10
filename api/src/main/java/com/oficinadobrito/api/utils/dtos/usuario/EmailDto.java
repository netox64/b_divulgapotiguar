package com.oficinadobrito.api.utils.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
        @NotBlank(message = "email is required")
        @Email(message = "Invalid email format")
        String email
) { }
