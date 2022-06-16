package com.senla.intership.boot;

import com.senla.intership.boot.dto.user.AuthorizedUserDTO;
import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
public class BootApplicationTests {

    protected UsernamePasswordAuthenticationToken authenticationToken;
    protected MockMvc mockMvc;
    protected UserProfile userProfile;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();

        userProfile = new UserProfile();
        userProfile.setFirstname("Admin");
        User user = new User();
        user.setProfile(userProfile);
        user.setUsername("username_");
        user.setPassword("username_password");
        userRepository.save(user);

        AuthorizedUserDTO authorizedUserDTO =
                new AuthorizedUserDTO(user.getId(), user.getUsername(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")));

        authenticationToken = new UsernamePasswordAuthenticationToken(
                authorizedUserDTO, null, authorizedUserDTO.getAuthorities());
    }
}
