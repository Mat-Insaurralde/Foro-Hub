package com.lastByte.Foro.Hub.controller;


import com.lastByte.Foro.Hub.domain.usuario.RequestRegistroUsuarioDTO;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioService;
import com.lastByte.Foro.Hub.domain.usuario.loginUsuarioDTO;
import com.lastByte.Foro.Hub.infra.security.AutenticationService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping()
public class AutenticacionController {


    @Autowired
    private AuthenticationManager authM;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutenticationService autenticationService;


    @PostMapping("/login")
    @Transactional
    public ResponseEntity loginUsuario(@RequestBody @Valid loginUsuarioDTO loginUsuarioDTO ) {
    // Va a checkear que el usuario exista comparar las contraseñas
    var usuario = usuarioService.validacionUsernameYPassword(loginUsuarioDTO);

    var authUsuario = autenticationService.autenticarYGenerarToken(loginUsuarioDTO.username(), loginUsuarioDTO.password(), authM );

    return  ResponseEntity.status(HttpStatus.OK).body(authUsuario);

    }




    @PostMapping("/register")
    @Transactional
    public ResponseEntity registrarUsuario(@RequestBody @Valid RequestRegistroUsuarioDTO registroUsuarioDTO ) {

        var response = usuarioService.registrarUsuario(registroUsuarioDTO);

        var authUsuario = autenticationService.autenticarYGenerarToken(registroUsuarioDTO.username(),registroUsuarioDTO.password(), authM );

        return  ResponseEntity.status(HttpStatus.CREATED).body(authUsuario);
    }



}
