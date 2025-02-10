package com.oficinadobrito.api.utils.dtos.categoria;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateCategoriaDto(
    String nome,
    String descricao
) {
}
