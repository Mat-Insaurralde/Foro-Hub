package com.lastByte.Foro.Hub.domain.topico;

import com.lastByte.Foro.Hub.controller.ResponseStandarDTO;
import com.lastByte.Foro.Hub.domain.curso.Curso;
import com.lastByte.Foro.Hub.domain.curso.CursoRepository;
import com.lastByte.Foro.Hub.domain.curso.CursoService;
import com.lastByte.Foro.Hub.domain.topico.validaciones.CursosCorrespondientes;
import com.lastByte.Foro.Hub.domain.usuario.Usuario;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioRepository;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioService;
import com.lastByte.Foro.Hub.infra.excepciones.ResourceNotFoundException;
import com.lastByte.Foro.Hub.infra.excepciones.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopicoService {


    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoService cursoService;
    @Autowired
    private CursosCorrespondientes cursosCorrespondientes;


    public DetalleTopicoDTO registrarTopico(RegistroTopicoDTO registroTopicoDTO) {

        var username = usuarioService.obtenerUsernameEnSesion();

        if (username == null) {
            throw new ValidacionDeIntegridad("Debe registrarse o loguearse!");
        }

        //BUSCAMOS EL USUARIO PARA GUARDARLO COMO EL AUTOR DEL TOPICO
        var usuario = (Usuario) usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new ValidacionDeIntegridad("Debe registrarse o loguearse!");
        }


        //VERIFICAR QUE EL TITULO Y MENSAJE NO SEA DUPLICADO
        if (topicoRepository.existsByTituloAndMensajeIgnoreCase(registroTopicoDTO.titulo(), registroTopicoDTO.mensaje())) {
            throw new ValidacionDeIntegridad("Ya existe un topico con el mismo titulo y mensaje");
        }


        //Creamos el topico
        var topico = new Topico(registroTopicoDTO);

        topico.setAutor(usuario);


        //VALIDAMOS QUE AL MENOS UN CURSO INGRESADO EXISTA
        List<Curso> cursos = cursosCorrespondientes.validar(registroTopicoDTO.cursos());


        //REGISTRAMOS EL TOPICO
        topicoRepository.save(topico);

        //REGISTRAMOS A QUE CURSOS SE VINCULA EL TOPICO
        for (Curso curso : cursos) {
            topicoRepository.registrarCursoDelTopico(topico.getId(), curso.getId());
        }


        //AGREGAMOS EL TOPICO AL USUARIO Y LO GUARDAMOS
        usuario.getTopicos().add(topico);
        usuarioRepository.save(usuario);


        return new DetalleTopicoDTO(topico, cursos);

    }


    public DetalleTopicoDTO buscarTopicoPorid(Long id) {

        if (id == null) {
            throw new ValidationException("No se ah ingresado un ID!");
        }

        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) throw new ResourceNotFoundException("Topico no encontrado con el ID proporcionado");

        var cursos = cursoRepository.findCursosByTopicoId(topico.get().getId());

        return new DetalleTopicoDTO(topico.get(), cursos);
    }


    public DetalleTopicoDTO actualizarTopicoPorId(Long id, ActualizarTopicoDTO actualizarTopicoDTO) {

        if (id == null) {
            throw new ValidationException("No se ah ingresado un ID!");
        }

        Optional<Topico> opTopico = topicoRepository.findById(id);

        if (opTopico.isEmpty()) {
            throw new ResourceNotFoundException("Topico no encontrado con el ID proporcionado");
        }

        List<Curso> cursosTopico = cursoRepository.findCursosByTopicoId(id);

        var topico = opTopico.get();

        List<Curso> cursosNuevos = new ArrayList<>();


        if (!actualizarTopicoDTO.cursos().isEmpty()) {
            //VALIDAMOS QUE AL MENOS UN CURSO INGRESADO EXISTA
            //VERIFICA LOS CURSOS Y LOS TRAE
            cursosNuevos = cursosCorrespondientes.validar(actualizarTopicoDTO.cursos());

            ///VALIDAMOS QUE LOS CURSOS SEAN DISTINTOS

            if (!contienenLosMismosCursos(cursosTopico, cursosNuevos)) {

                ///ELIMINAMOS LAS REFERENCIAS DE CURSOS AL TOPICO
                topicoRepository.eliminarCursosDelTopico(id);
                //REGISTRAMOS LOS NUEVOS CURSOS DEL TOPICO
                for (Curso curso : cursosNuevos) {
                    topicoRepository.registrarCursoDelTopico(id, curso.getId());
                }

            }
        }

        topico.actualizarDatos(actualizarTopicoDTO);

        topicoRepository.save(topico);

        return new DetalleTopicoDTO(topico, cursosNuevos);
    }


    public String eliminarTopicoPorId(Long id) {

        if (!topicoRepository.existsById(id)){
            throw new ResourceNotFoundException("Topico no encontrado con el ID proporcionado");
        }
        topicoRepository.deleteById(id);

        ///ELIMINAMOS LAS REFERENCIAS DE CURSOS AL TOPICO
        topicoRepository.eliminarCursosDelTopico(id);

        return "Topico eliminado con exito!";
    }








    public static boolean contienenLosMismosCursos(List<Curso> cursosTopico, List<Curso> cursosNuevos) {

        cursosTopico.sort(Comparator.comparing(Curso::getNombre));
        cursosNuevos.sort(Comparator.comparing(Curso::getNombre));

        if (cursosTopico.equals(cursosNuevos)) {
            return true;
        } else {
            return false;
        }
    }


}
