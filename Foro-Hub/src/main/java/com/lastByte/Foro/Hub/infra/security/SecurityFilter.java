package com.lastByte.Foro.Hub.infra.security;

import com.lastByte.Foro.Hub.domain.usuario.UsuarioRepository;
import com.lastByte.Foro.Hub.infra.excepciones.TokenVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener el token del header
        var authHeader = request.getHeader("Authorization");


        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
         
            var username = tokenService.getSubject(token); //Extraemos username o login


            if (username != null) {
                //token valido
                var usuario = usuarioRepository.findByUsername(username);
                //forzamos autenticacion del usuario
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //Forzamos inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


        }

        filterChain.doFilter(request, response);


    }


}
