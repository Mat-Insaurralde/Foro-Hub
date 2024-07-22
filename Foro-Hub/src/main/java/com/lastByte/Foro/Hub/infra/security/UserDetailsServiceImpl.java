package com.lastByte.Foro.Hub.infra.security;

import com.lastByte.Foro.Hub.Utils.JwtUtils;
import com.lastByte.Foro.Hub.controller.dto.AuthLoginRequest;
import com.lastByte.Foro.Hub.controller.dto.AuthRegisterUserRequest;
import com.lastByte.Foro.Hub.controller.dto.AuthResponse;
import com.lastByte.Foro.Hub.domain.usuario.Roles.Role;
import com.lastByte.Foro.Hub.domain.usuario.Roles.RoleRepository;
import com.lastByte.Foro.Hub.domain.usuario.Usuario;
import com.lastByte.Foro.Hub.domain.usuario.UsuarioRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
    }



    /// INICIAR SESION

    public AuthResponse login(AuthLoginRequest datosLogin) {

        Authentication authentication = authenticate(datosLogin.username(), datosLogin.password());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthResponse(datosLogin.username(), "Usuario logeado con exito!",  true);
    }





    ///REGISTRAR USUARIO

    public AuthResponse registerUser(AuthRegisterUserRequest registerUserRequest) {

        if (usuarioRepository.existsByEmail(registerUserRequest.email())) {
            throw new ValidationException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.existsByUsername(registerUserRequest.username())) {
            throw new ValidationException("Ya existe un usuario con ese username");
        }

        List<String> roleRequest = registerUserRequest.roleRequest().roleList();

         Set<Role> rolesSet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

         if (rolesSet.isEmpty()){
             throw new ValidationException("Los roles especificados no existen");
         }

        var usuario = new Usuario(rolesSet,registerUserRequest, passwordEncoder.encode(registerUserRequest.password()));

        usuarioRepository.save(usuario);

        return new AuthResponse(registerUserRequest.username(), "Usuario registrado con exito!",  true);
    }





    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Username o password invalido!");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password invalido!");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }




    public String createToken(String username, String password){
        Authentication authenticate = authenticate(username,password);
        return jwtUtils.createToken(authenticate);
    }










}
