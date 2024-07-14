package com.lastByte.Foro.Hub.domain.topico;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record RegistroTopicoDTO(

@NotBlank
String titulo,

@NotBlank
String mensaje,

@NotEmpty
List<String> cursos


) {
}
