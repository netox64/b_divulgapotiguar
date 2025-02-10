package com.oficinadobrito.api.utils.dtos.usuario;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oficinadobrito.api.utils.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUsuarioDto(
        String username,
        String image,
        String phone,
        @NotBlank(message = "pasword is required")
        String password,
        UserRole role,
        String token,
        BigInteger[] notificacoes,
        BigInteger[] planos
) {
}