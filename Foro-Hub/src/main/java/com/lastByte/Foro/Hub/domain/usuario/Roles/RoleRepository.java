package com.lastByte.Foro.Hub.domain.usuario.Roles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

}
