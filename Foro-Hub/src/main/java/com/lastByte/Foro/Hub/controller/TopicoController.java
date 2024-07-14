package com.lastByte.Foro.Hub.controller;


import com.lastByte.Foro.Hub.domain.topico.RegistroTopicoDTO;
import com.lastByte.Foro.Hub.domain.topico.TopicoService;
import jakarta.transaction.Transactional;
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
    public  ResponseEntity buscarTopicoPorId(@PathVariable Long id ){
       var detalleTopico = topicoService.buscarTopicoPorid(id);
       return ResponseEntity.status(HttpStatus.OK).body(detalleTopico);
    }






}
