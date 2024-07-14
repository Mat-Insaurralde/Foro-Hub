package com.lastByte.Foro.Hub.domain.curso;

import java.time.LocalDateTime;

public record DetalleCursoDTO(
        Long id,
        String nombre,
        String descripcion,
        LocalDateTime fechaDeCreacion

) {
    public DetalleCursoDTO(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getDescripcion(), curso.getFechaDeCreacion());
    }
}
