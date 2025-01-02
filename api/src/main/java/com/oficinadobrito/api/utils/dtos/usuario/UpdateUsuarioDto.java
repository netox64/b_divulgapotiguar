package com.oficinadobrito.api.utils.dtos.usuario;

import com.oficinadobrito.api.utils.enums.UserRole;

public record UpdateUsuarioDto(
        String password,
        UserRole role
) { }
