package com.lastByte.Foro.Hub.domain.topico;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record RegistroTopicoDTO(

@NotBlank(message = "El titulo del topico es obligatorio")
String titulo,

@NotBlank(message = "El mensaje del topico es obligatorio")
String mensaje,

@NotEmpty(message = "Los cursos del topico son obligatorios")
List<String> cursos


) {
}
