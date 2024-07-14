package com.lastByte.Foro.Hub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  //Spring escanea primero al ser una configuracion
@EnableWebSecurity //indica que vamos a sobreescribir el metodo de autenticacion con el que queremos
public class SecurityConfigurations {


    @Autowired
    private SecurityFilter securityFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()) //es para evitar suplantacion de identidad
                //lo deshabilito porque vamos a usar aut token
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/login").permitAll() //Permite a tods ingresar al POST /login
                        .requestMatchers(HttpMethod.POST,"/register").permitAll() //Permite a tods ingresar al POST /login
                        .requestMatchers("/swagger-ui.html","/v3/api-docs/**","/swagger-ui/**").permitAll()
                        .anyRequest()
                        .authenticated()) //Todos los demas request deben ser autenticados
                /*Agrega mi filtro antes de que lanze su filtro*/
                .addFilterBefore(securityFilter,
                        UsernamePasswordAuthenticationFilter.class )//Valida que el usuario tenga una sesion iniciada
                .build();
    }





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
