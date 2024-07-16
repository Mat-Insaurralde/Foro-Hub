package com.lastByte.Foro.Hub.controller;


import com.lastByte.Foro.Hub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/topicos")
@Tag(name = "Topico", description = "Endpoints para la gestión de tópicos")
public class TopicoController {


    @Autowired
    TopicoService topicoService;



    @PostMapping
    @Transactional
    @Operation(
            summary = "Registrar un nuevo tópico",
            description = "Este endpoint permite registrar un nuevo tópico en el sistema.",
            tags = {"Topico"}
    )
    public ResponseEntity registrarTopico(@RequestBody RegistroTopicoDTO registroTopicoDTO) {
        var response = topicoService.registrarTopico(registroTopicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar un tópico por ID",
            description = "Este endpoint permite obtener los detalles de un tópico específico mediante su ID.",
            tags = {"Topico"}
    )
    public  ResponseEntity<DetalleTopicoDTO> buscarTopicoPorId(@PathVariable Long id ){
       var detalleTopico = topicoService.buscarTopicoPorid(id);
       return ResponseEntity.status(HttpStatus.OK).body(detalleTopico);
    }



    @GetMapping
    @Operation(
            summary = "Listar todos los tópicos",
            description = "Este endpoint permite listar todos los tópicos registrados en el sistema.",
            tags = {"Topico"}
    )
    public  ResponseEntity<List<DetalleTopicoDTO>> buscarTodosLosTopicos(){
        var response = topicoService.buscarTodosLosTopicos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/por_fecha_asc")
    @Operation(
            summary = "Buscar los primeros 10 tópicos por fecha de creación ascendente",
            description = "Este endpoint permite obtener los primeros 10 tópicos ordenados por fecha de creación en orden ascendente.",
            tags = {"Topico"}
    )
    public  ResponseEntity <Page<DetalleTopicoDTO>> buscarTop10PorFechaDeCreacionAsc(@PageableDefault(size = 10) Pageable paginacion){
        var response = topicoService.buscarTop10TopicosPorFechaCreacionASC(paginacion);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/por_criterio_busqueda")
    @Operation(
            summary = "Buscar tópicos por nombre y año",
            description = "Este endpoint permite buscar tópicos filtrados por nombre y año de creación.",
            tags = {"Topico"}
    )
    public  ResponseEntity <Page<DetalleTopicoDTO>> buscarTopicoPorNombreYanio(
            @PageableDefault(size = 5) Pageable paginacion , @RequestBody RequestTopicoAnioYfechaDTO datosRequest){
        var response = topicoService.buscarTopicosPorNombreYAnio(paginacion , datosRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




    @PutMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Eliminar un tópico por ID",
            description = "Este endpoint permite eliminar un tópico del sistema mediante su ID.",
            tags = {"Topico"}
    )
    public ResponseEntity<DetalleTopicoDTO> actualizarTopicoPorId(@PathVariable Long id, @RequestBody ActualizarTopicoDTO actualizarTopicoDTO ){
       var response = topicoService.actualizarTopicoPorId(id,actualizarTopicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarTopicoPorId(@PathVariable Long id ){
        var response = topicoService.eliminarTopicoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }





}
