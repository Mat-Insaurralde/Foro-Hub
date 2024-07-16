package com.lastByte.Foro.Hub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record RegistroCursoDTO(
        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombre,
        @NotBlank(message = "La descripcion del curso es obligatoria")
        String descripcion
) {
}
