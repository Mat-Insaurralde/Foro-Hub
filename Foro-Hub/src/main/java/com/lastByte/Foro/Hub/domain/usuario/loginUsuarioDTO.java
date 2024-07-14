package com.lastByte.Foro.Hub.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record loginUsuarioDTO(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
