package com.lastByte.Foro.Hub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record RegistroCursoDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String descripcion
) {
}
