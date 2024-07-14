package com.lastByte.Foro.Hub.domain.usuario;


import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public DetalleUsuarioDTO registrarUsuario(RequestRegistroUsuarioDTO registroUsuarioDTO) {

        if (usuarioRepository.existsByEmail(registroUsuarioDTO.email())){
            throw new ValidationException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.existsByUsername(registroUsuarioDTO.username())){
            throw new ValidationException("Ya existe un usuario con ese username");
        }

         var usuario = new Usuario(registroUsuarioDTO,passwordEncoder.encode(registroUsuarioDTO.password()));

        usuarioRepository.save(usuario);

        return new DetalleUsuarioDTO(usuario);
    }




    public boolean validacionUsernameYPassword(loginUsuarioDTO loginUsuarioDTO) {

        //Buscamos el usuario
        var usuario = (Usuario) usuarioRepository.findByUsername(loginUsuarioDTO.username());

        if (usuario==null){
            throw new ValidationException("No se encontro usuario con ese username!");
        }

        if (usuario != null){
            //comparamos las contraseñas
            if(!passwordEncoder.matches(loginUsuarioDTO.password(),usuario.getPassword() ) ){
                throw new ValidationException("Userbane o contraseña incorrecta!");
            }
            return true;
        }
        return false;
    }




   public String obtenerUsernameEnSesion(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return null;
    }










}
