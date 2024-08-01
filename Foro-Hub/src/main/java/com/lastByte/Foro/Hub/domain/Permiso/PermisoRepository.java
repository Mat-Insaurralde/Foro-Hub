package com.lastByte.Foro.Hub.domain.Permiso;


import com.lastByte.Foro.Hub.domain.Role.Role;
import com.lastByte.Foro.Hub.domain.Role.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso,Long> {


    List<Permiso> findPermisoEntitiesByPermisoEnumIn(List<String> permisosName);


    Optional<Permiso> findByPermisoEnum(PermisoEnum name);



}
