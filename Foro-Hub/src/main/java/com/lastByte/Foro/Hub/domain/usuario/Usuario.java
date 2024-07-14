package com.lastByte.Foro.Hub.domain.usuario;


import com.lastByte.Foro.Hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String username;
    private String password;

    private Boolean status;

    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;


    public Usuario(RequestRegistroUsuarioDTO registroUsuarioDTO, String password) {
        this.nombre= registroUsuarioDTO.nombre();
        this.email=registroUsuarioDTO.email();
        this.username=registroUsuarioDTO.username();
        this.password=password;
        this.status=true;
    }


    public void desactivarUsuario() {
        this.status=false;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }




}
