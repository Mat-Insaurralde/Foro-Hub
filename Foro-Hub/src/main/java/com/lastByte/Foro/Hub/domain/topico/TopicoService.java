package com.lastByte.Foro.Hub.domain.topico;

import com.lastByte.Foro.Hub.controller.ResponseStandarDTO;
import com.lastByte.Foro.Hub.domain.curso.Curso;
import com.lastByte.Foro.Hub.domain.curso.CursoRepository;
import com.lastByte.Foro.Hub.domain.curso.CursoService;
import com.lastByte.Foro.Hub.domain.topico.validaciones.CursosCorrespondientes;
import com.lastByte.Foro.Hub.domain.usuario.Usuario;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioRepository;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioService;
import com.lastByte.Foro.Hub.infra.excepciones.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) throw new ValidationException("Topico no encontrado!");

        var cursos = cursoRepository.findCursosByTopicoId(topico.get().getId());

        return new DetalleTopicoDTO(topico.get(), cursos);
    }


}
