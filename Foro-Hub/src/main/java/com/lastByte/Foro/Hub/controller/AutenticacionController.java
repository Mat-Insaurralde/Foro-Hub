package com.lastByte.Foro.Hub.controller;


import com.lastByte.Foro.Hub.domain.usuario.RequestRegistroUsuarioDTO;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioService;
import com.lastByte.Foro.Hub.domain.usuario.loginUsuarioDTO;
import com.lastByte.Foro.Hub.infra.security.AutenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autentiacion", description = "Endpoints para el inicio de sesion o registro del usuario")
public class AutenticacionController {


    @Autowired
    private AuthenticationManager authM;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutenticationService autenticationService;


    @PostMapping("/login")
    @Transactional
    @Operation(
            summary = "Obtiene el acceso , la autenticacion y el token para el usuario ingresado que da acceso a los demas endpoints",
            description = """
                          Este endpoint permite a un usuario autenticarse en el sistema y obtener un token JWT.
                          El token se debe incluir en los encabezados de las solicitudes subsecuentes para acceder a los endpoints protegidos.""",
            tags = {}
    )
    public ResponseEntity loginUsuario(@RequestBody @Valid loginUsuarioDTO loginUsuarioDTO ) {
    // Va a checkear que el usuario exista comparar las contraseñas
    var usuario = usuarioService.validacionUsernameYPassword(loginUsuarioDTO);

    var authUsuario = autenticationService.autenticarYGenerarToken(loginUsuarioDTO.username(), loginUsuarioDTO.password(), authM );

    return  ResponseEntity.status(HttpStatus.OK).body(authUsuario);

    }




    @PostMapping("/register")
    @Transactional
    @Operation(
            summary = "Registra un nuevo usuario , obtiene la autenticacion y token para el usuario",
            description = """
                    Este endpoint permite registrar un nuevo usuario en el sistema. 
                    Al registrarse, el usuario recibe un token JWT que le da acceso a los endpoints protegidos.
                    """,
            tags = {}
    )
    public ResponseEntity registrarUsuario(@RequestBody @Valid RequestRegistroUsuarioDTO registroUsuarioDTO ) {

        var response = usuarioService.registrarUsuario(registroUsuarioDTO);

        var authUsuario = autenticationService.autenticarYGenerarToken(registroUsuarioDTO.username(),registroUsuarioDTO.password(), authM );

        return  ResponseEntity.status(HttpStatus.CREATED).body(authUsuario);
    }



}
