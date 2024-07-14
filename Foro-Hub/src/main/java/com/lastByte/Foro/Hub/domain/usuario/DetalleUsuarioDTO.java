package com.lastByte.Foro.Hub.domain.usuario;

public record DetalleUsuarioDTO(
        Long id,
        String nombre,
        String email,
        String username

)
{
    public DetalleUsuarioDTO(Usuario autor) {
        this(autor.getId(), autor.getNombre(), autor.getEmail(), autor.getUsername());
    }
}
