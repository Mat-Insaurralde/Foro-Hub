package com.lastByte.Foro.Hub.domain.topico;


//import com.lastByte.Foro.Hub.domain.curso.Curso;
import com.lastByte.Foro.Hub.domain.curso.Curso;
import com.lastByte.Foro.Hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Table(name = "topicos")
@Entity(name = "topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Topico {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;
    private boolean status ;

    @Setter
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;


    //No mapeo los cursos porque usare una tabla intermedia



    public Topico(RegistroTopicoDTO registroTopicoDTO) {
        this.titulo = registroTopicoDTO.titulo();
        this.mensaje = registroTopicoDTO.mensaje();
        this.fechaDeCreacion = LocalDateTime.now();
        this.status=true;

    }


    @Override
    public String toString() {
        return "Topico: " +
               "Titulo: " + titulo + '\'' +
               "Mensaje: " + mensaje + '\'' +
               "Fecha De Creacion: " + fechaDeCreacion +
               "Autor: " + autor.getEmail() ;
    }
}
