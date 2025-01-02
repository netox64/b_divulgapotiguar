package com.oficinadobrito.api.utils.dtos.feedback;

import jakarta.validation.constraints.NotNull;

public record DeleteFeedbackDto(
        @NotNull(message = "Usuario for deleting is required")
        String usuarioRequestId
) { }
