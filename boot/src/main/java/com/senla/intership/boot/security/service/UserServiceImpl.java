package com.senla.intership.boot.security.service;

import com.senla.intership.boot.dto.user.LoginDto;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.dto.user.UserWithRolesDto;
import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.exceptions.custom.AuthException;
import com.senla.intership.boot.repository.UserRepository;
import com.senla.intership.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ModelMapper mapper;

    @Value("${user.url}")
    private String url;

    @Override
    @Transactional
    public UserWithRolesDto signUp(LoginDto dto) {
        if (userRepository.getByNameWithRoles(dto.getUsername()) == null) {
            ResponseEntity<UserWithRolesDto> response = restTemplate.postForEntity(url + "/signup", dto, UserWithRolesDto.class);
            return response.getBody();
        }
        throw new AuthException("User " + dto.getUsername() + " already exist");
    }

    @Override
    @Transactional
    public String signIn(LoginDto dto) {
        if (userRepository.getByNameWithRoles(dto.getUsername()) != null) {
            ResponseEntity<String> response = restTemplate.postForEntity(url + "/token", dto, String.class);
            return response.getBody();
        }
        throw new AuthException("Invalid login or password");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserWithAllDto loadByUsername(String username) {
        User user = userRepository.getByName(username);
        return mapper.map(user, UserWithAllDto.class);
    }

}
