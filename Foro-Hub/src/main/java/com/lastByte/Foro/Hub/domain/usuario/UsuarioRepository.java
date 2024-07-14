package com.lastByte.Foro.Hub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuario,Long> {

   UserDetails findByUsername(String username);

    Optional<Usuario> findByEmailAndUsername(String email, String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);


    boolean existsByEmailAndUsername(String email, String username);

}
