package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.entity.Role;
import com.senla.intership.boot.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {BootApplication.class})
@DataJpaTest
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    private Role userRole;
    private Role adminRole;

    @BeforeEach
    public void init() {
        if (roleRepository.findAll().size() == 0) {
            userRole = new Role();
            userRole.setRoleName(RoleName.ROLE_USER);
            userRole = roleRepository.save(userRole);

            adminRole = new Role();
            adminRole.setRoleName(RoleName.ROLE_ADMIN);
            adminRole = roleRepository.save(adminRole);
        } else {
            userRole = roleRepository.getById(1L);
            adminRole = roleRepository.getById(2L);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(userRole.getId());
        assertNotNull(adminRole.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final Role potentialRole = roleRepository.findById(userRole.getId()).orElse(new Role());
        final Role potentialRole2 = roleRepository.findById(adminRole.getId()).orElse(new Role());
        assertEquals(userRole.getId(), potentialRole.getId());
        assertEquals(adminRole.getId(), potentialRole2.getId());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        RoleName roleName = RoleName.ROLE_ADMIN;
        userRole.setRoleName(roleName);
        roleRepository.save(userRole);

        assertEquals(userRole.getRoleName(), roleName);
    }

    @Test
    public void shouldFindHashtagByRoleNameCorrect() {
        final Role potentialRole = roleRepository.findRoleByRoleName(RoleName.ROLE_USER);
        final Role potentialRole2 = roleRepository.findRoleByRoleName(RoleName.ROLE_ADMIN);
        assertEquals(userRole.getRoleName(), potentialRole.getRoleName());
        assertEquals(adminRole.getRoleName(), potentialRole2.getRoleName());
    }
}
