package com.lastByte.Foro.Hub.controller;


import com.lastByte.Foro.Hub.domain.curso.CursoService;
import com.lastByte.Foro.Hub.domain.curso.DetalleCursoDTO;
import com.lastByte.Foro.Hub.domain.curso.RegistroCursoDTO;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/cursos")
public class CursoController {


    @Autowired
    private CursoService cursoService;



   //REGISTRAR CURSO
    @PostMapping
    @Transactional
    public ResponseEntity<DetalleCursoDTO> registrarCurso(@RequestBody RegistroCursoDTO registroCursoDTO){
        var response = cursoService.registrarCurso(registroCursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }

   //DELETE REAL
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarCurso(@PathVariable Long id){
        var response = cursoService.eliminarCurso(id);
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }


    ///LISTA DETALLE DEL CURSO POR ID
   @GetMapping("/{id}")
    public  ResponseEntity<DetalleCursoDTO> listarCursoPorId(@PathVariable Long id){
        var response = cursoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
   }


   //LISTA DETALLE DE TODOS LOS CURSOS
    @GetMapping()
    public  ResponseEntity listarTodosLosCursos(){
        var response = cursoService.listarTodosLosCursos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
