package com.oficinadobrito.api.utils.dtos.usuario;

public record RedemPasswordDto(
        String token,
        String password,
        String rePassword) {
}
