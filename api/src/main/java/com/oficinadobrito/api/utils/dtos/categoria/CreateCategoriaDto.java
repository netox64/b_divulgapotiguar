package com.oficinadobrito.api.utils.dtos.categoria;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoriaDto(
        @NotBlank(message = "categoria name is required") String nome,
        @NotBlank(message = "categoria descricao is required") String descricao
        
) { }
