package com.lastByte.Foro.Hub.domain.topico;

import com.lastByte.Foro.Hub.domain.curso.Curso;
import com.lastByte.Foro.Hub.domain.usuario.DetalleUsuarioDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DetalleTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String autor,
        List<String> cursos

) {
    public DetalleTopicoDTO(Topico topico  , List<Curso> cursos   ) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), topico.getAutor().getNombre(),
                cursos.stream()
                        .map(Curso::getNombre)
                        .collect(Collectors.toList())
                );


    }

}



