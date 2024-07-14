package com.lastByte.Foro.Hub.domain.usuario;

public record RequestRegistroUsuarioDTO(
        String nombre,
        String email,
        String username,
        String password

) {
}
