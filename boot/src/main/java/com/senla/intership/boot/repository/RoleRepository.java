package com.senla.intership.boot.repository;

import com.senla.intership.boot.entity.Role;
import com.senla.intership.boot.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(RoleName roleName);
}
