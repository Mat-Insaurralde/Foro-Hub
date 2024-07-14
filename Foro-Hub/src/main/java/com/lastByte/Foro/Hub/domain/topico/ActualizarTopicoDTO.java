package com.lastByte.Foro.Hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ActualizarTopicoDTO(

        String titulo,

        String mensaje,

        List<String> cursos
) {
}
