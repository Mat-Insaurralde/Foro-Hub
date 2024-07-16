package com.lastByte.Foro.Hub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record RequestRegistroUsuarioDTO(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String nombre,
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "El username del usuario es obligatorio")
        String username,
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}
