package com.azati.microservice.repository;

import com.azati.microservice.model.Role;
import com.azati.microservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByRoleName(RoleName roleName);
}
