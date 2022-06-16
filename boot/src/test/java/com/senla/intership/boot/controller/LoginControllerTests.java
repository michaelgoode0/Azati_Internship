package com.senla.intership.boot.controller;

import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.entity.Role;
import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.enums.RoleName;
import com.senla.intership.boot.repository.RoleRepository;
import com.senla.intership.boot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Collections;

@Transactional
public class LoginControllerTests extends BootApplicationTests {

    private static final String ilyaUsername = "ilya";
    private static final String ilyaPassword = "ilya_password";
    private static final String kirillUsername = "kirill";
    private static final String kirillPassword = "kirill_password";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private User ilya;
    private User kirill;

    @BeforeEach
    public void init() {
        Role role = new Role();
        role.setRoleName(RoleName.ROLE_USER);
        UserProfile ilyaProfile = new UserProfile();
        ilyaProfile.setFirstname("Ilya");
        ilya = new User();
        ilya.setUsername(ilyaUsername);
        ilya.setPassword(ilyaPassword);
        ilya.setProfile(ilyaProfile);
        ilya.setRoles(Collections.singleton(role));
        userRepository.save(ilya);

        UserProfile kirillProfile = new UserProfile();
        kirillProfile.setFirstname("Kirill");
        kirill = new User();
        kirill.setUsername(kirillUsername);
        kirill.setPassword(kirillPassword);
        kirill.setProfile(kirillProfile);

        userRepository.save(kirill);
    }

   /* @Test
    public void shouldSignUpCorrectly() throws Exception {
        String userDto = """
                {
                     "username": "Matvey",
                     "password" : "123"
                }
                """;
        mockMvc.perform(
                post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userDto)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("Matvey"))
                .andExpect(jsonPath("$.password").value("123"));
    }*/

   /* @Test
    public void shouldGetToken() throws Exception { ;
        String userDto = """
                {
                     "username": "ilya",
                     "password" : "ilya_password"
                }
                """;
        mockMvc.perform(
                post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userDto))

                .andDo(print());*/
}

