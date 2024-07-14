package com.lastByte.Foro.Hub.domain.curso;


import com.lastByte.Foro.Hub.controller.ResponseStandarDTO;
import com.lastByte.Foro.Hub.infra.excepciones.CollectionEmptyException;
import com.lastByte.Foro.Hub.infra.excepciones.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.hibernate.query.NotIndexedCollectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;


    public DetalleCursoDTO registrarCurso(RegistroCursoDTO registroCursoDTO) {

        if (cursoRepository.existsByNombreIgnoreCase(registroCursoDTO.nombre())) {
            throw new ValidacionDeIntegridad("Ya existe un curso con ese nombre!");
        }

        var curso = new Curso(registroCursoDTO);

        cursoRepository.save(curso);

        return new DetalleCursoDTO(curso);
    }


    public String eliminarCurso(Long id) {

        var curso = cursoRepository.findById(id);

        if (curso.isEmpty()) {
            throw new ValidationException("Curso no encontrado!");
        }

        cursoRepository.delete(curso.get());

        return "Curso eliminado con exito";
    }


    public List<DetalleCursoDTO> listarTodosLosCursos() {
        var cursos = cursoRepository.findAll();

        if (cursos.isEmpty()) {
            throw new CollectionEmptyException("No tiene cursos registrados!");
        }

        var cursosDetalle = cursos.stream()
                .map(DetalleCursoDTO::new)
                .collect(Collectors.toList());

        return cursosDetalle;
    }


    public DetalleCursoDTO findById(Long id) {
        var curso = cursoRepository.findById(id);

        if (curso.isEmpty()) {
            throw new ValidationException("Curso no encontrado!");
        }

        return new DetalleCursoDTO(curso.get());
    }
}
