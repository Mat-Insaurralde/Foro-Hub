package com.lastByte.Foro.Hub.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);

    Optional<Role> findByRoleEnum(RoleEnum name);

}
