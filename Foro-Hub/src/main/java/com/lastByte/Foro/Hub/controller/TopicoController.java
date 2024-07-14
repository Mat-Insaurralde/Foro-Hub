package com.lastByte.Foro.Hub.controller;


import com.lastByte.Foro.Hub.domain.topico.ActualizarTopicoDTO;
import com.lastByte.Foro.Hub.domain.topico.DetalleTopicoDTO;
import com.lastByte.Foro.Hub.domain.topico.RegistroTopicoDTO;
import com.lastByte.Foro.Hub.domain.topico.TopicoService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/topicos")
public class TopicoController {


    @Autowired
    TopicoService topicoService;



    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody RegistroTopicoDTO registroTopicoDTO) {
        var response = topicoService.registrarTopico(registroTopicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{id}")
    public  ResponseEntity<DetalleTopicoDTO> buscarTopicoPorId(@PathVariable Long id ){
       var detalleTopico = topicoService.buscarTopicoPorid(id);
       return ResponseEntity.status(HttpStatus.OK).body(detalleTopico);
    }


    @PutMapping("/{id}")
    @Transactional
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
