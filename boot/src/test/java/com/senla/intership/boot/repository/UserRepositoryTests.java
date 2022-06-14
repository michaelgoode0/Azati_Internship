package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.api.repository.UserRepository;
import com.senla.intership.boot.api.repository.impl.UserRepositoryImpl;
import com.senla.intership.boot.security.enums.RoleName;
import com.senla.intership.boot.entity.Role;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {BootApplication.class, UserRepositoryImpl.class})
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User ilya;
    private User kirill;
    private static final String ilyaUsername="ilya";
    private static final String ilyaPassword="ilya_password";
    private static final String kirillUsername="kirill";
    private static final String kirillPassword="kirill_password";

    @BeforeEach
    public void init() {
        if (userRepository.getAll().size()==0) {
            UserProfile ilyaProfile = new UserProfile();
            ilyaProfile.setFirstname("Ilya");
            ilya = new User();
            ilya.setUsername(ilyaUsername);
            ilya.setPassword(ilyaPassword);
            ilya.setProfile(ilyaProfile);

            userRepository.save(ilya);

            UserProfile kirillProfile = new UserProfile();
            kirillProfile.setFirstname("Kirill");
            kirill = new User();
            kirill.setUsername(kirillUsername);
            kirill.setPassword(kirillPassword);
            kirill.setProfile(kirillProfile);

            userRepository.save(kirill);
        } else {
            ilya = userRepository.get(1L);
            kirill = userRepository.get(2L);
        }
    }
    @Test
    public void hibernateShouldSetIdWhenUserSaved() {
        assertEquals(ilya.getId(),1L);
        assertNotNull(kirill.getId());
    }

    @Test
    public void shouldFindUserByIdCorrect() {
        final User potentialMike = userRepository.get(ilya.getId());
        final User potentialNick = userRepository.get(kirill.getId());
        assertEquals(ilya.getId(), potentialMike.getId());
        assertEquals(kirill.getId(), potentialNick.getId());
    }

    @Test
    public void shouldFindUserByLoginCorrect() {

        final User potentialMike = userRepository.getByName(ilyaUsername);
        final User potentialNick = userRepository.getByName(kirillUsername);

        assertEquals(ilya.getId(), potentialMike.getId());
        assertEquals(kirill.getId(), potentialNick.getId());
        assertEquals(ilya.getUsername(), potentialMike.getUsername());
        assertEquals(kirill.getUsername(), potentialNick.getUsername());
    }

    @Test
    public void shouldFindUserByLoginJdbcCorrect() { // how to get connection

        final User potentialMike = userRepository.getByLoginJdbc(ilyaUsername);
        final User potentialNick = userRepository.getByLoginJdbc(kirillUsername);

        assertEquals(ilya.getId(), potentialMike.getId());
        assertEquals(kirill.getId(), potentialNick.getId());
        assertEquals(ilya.getUsername(), potentialMike.getUsername());
        assertEquals(kirill.getUsername(), potentialNick.getUsername());
    }

    @Test
    public void shouldFindEagerUsersCorrect() {
        Role role = new Role();
        RoleName roleName = RoleName.ROLE_USER;
        role.setRoleName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        ilya.setRoles(roles);

        userRepository.update(ilya);
        final User mikeCriteriaEager = userRepository.getEagerCriteria(ilya.getId());
        final User mikeJPQLEager = userRepository.getEagerJPQL(ilya.getId());
        final User mikeGraphEager = userRepository.getEagerGraph(ilya.getId());

        assertEquals(mikeCriteriaEager.getRoles(), roles);
        assertEquals(mikeJPQLEager.getRoles(), roles);
        assertEquals(mikeGraphEager.getRoles(), roles);

    }

    @Test
    public void shouldFinishWithLazyException() {
        Role role = new Role();
        RoleName roleName = RoleName.ROLE_USER;
        role.setRoleName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        ilya.setRoles(roles);
        userRepository.update(ilya);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertThrows(
                LazyInitializationException.class,
                () -> userRepository.get(ilya.getId()).getRoles().forEach(Role::getRoleName));
        TestTransaction.start();
    }

    @Test
    public void hibernateShouldUpdateUser() {

        UserProfile userProfile = new UserProfile();
        String name="alesha";
        userProfile.setFirstname(name);
        ilya.setProfile(userProfile);
        userRepository.update(ilya);

        assertEquals(ilya.getProfile().getFirstname(),name);
    }
   /* @Test
    public void userShouldBeDeletedSuccess() {
        User user = new User();
        User savedUser =userRepository.save(user);
        userRepository.deleteById(savedUser.getId());
        User deletedUser = userRepository.get(savedUser.getId());
        assertNull(deletedUser);
    }*/


}
