package com.lastByte.Foro.Hub.infra.security;

import com.lastByte.Foro.Hub.domain.usuario.ResponseAuthUsuarioDTO;
import com.lastByte.Foro.Hub.domain.usuario.Usuario;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService implements UserDetailsService {

@Autowired
private UsuarioRepository usuarioRepository;



    @Autowired
    private TokenService tokenService;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  usuarioRepository.findByUsername(username);
    }


    public ResponseAuthUsuarioDTO autenticarYGenerarToken(String username , String password, AuthenticationManager authenticationManager) {
        try {
            // Crear el token de autenticación con las credenciales del usuario
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );

            // Autenticar al usuario
            Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

            // Generar el token para el usuario autenticado
            String token = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

            // Devolver la respuesta con el token generado
            return new ResponseAuthUsuarioDTO("¡Usuario autenticado con éxito!", token);


        } catch (AuthenticationException e) {
            // Manejo de excepciones de autenticación
            return new ResponseAuthUsuarioDTO("Error de autenticación: " + e.getMessage(), null);

        } catch (Exception e) {
            // Manejo de otras excepciones
            return new ResponseAuthUsuarioDTO("Error inesperado: " + e.getMessage(), null);

        }
    }











}
