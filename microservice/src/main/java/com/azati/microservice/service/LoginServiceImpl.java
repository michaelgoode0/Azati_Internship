package com.azati.microservice.service;

import com.azati.microservice.dto.LoginDto;
import com.azati.microservice.dto.UserDto;
import com.azati.microservice.filter.TokenProvider;
import com.azati.microservice.model.Role;
import com.azati.microservice.model.RoleName;
import com.azati.microservice.model.User;
import com.azati.microservice.repository.RoleRepository;
import com.azati.microservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService, UserDetailsService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final AuthenticationManagerBuilder authenticationManager;

    @Transactional
    @Override
    public String signIn(LoginDto dto) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        final Authentication authentication = authenticationManager.getObject().authenticate(authenticationToken);
        return tokenProvider.createToken(authentication);
    }

    @Override
    @Transactional
    @RabbitListener(queues = "Queue 1")
    public UserDto signUp(LoginDto dto) {
        User user = new User();
        Role userRole = roleRepository.findRoleByRoleName(RoleName.ROLE_USER);
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singleton(userRole));
        User response = userRepository.save(user);
        return mapper.map(response, UserDto.class);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
