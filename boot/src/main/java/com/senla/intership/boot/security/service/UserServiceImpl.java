package com.senla.intership.boot.security.service;

import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.api.repository.UserRepository;
import com.senla.intership.boot.api.service.UserService;
import com.senla.intership.boot.dto.user.LoginDto;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.security.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {
    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserWithAllDto signUp(LoginDto dto, RoleName roleName) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserWithAllDto> response = restTemplate.postForEntity("https://auth:8081/registration",dto,UserWithAllDto.class);
        return response.getBody();
    }

    @Override
    @Transactional
    public String signIn(LoginDto dto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity( "https://auth:8081/create",dto,String.class);
        return response.getBody();
    }

    @Override
    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserWithAllDto loadByUsername(String username) {
        User user = userRepository.getByName(username);
        return mapper.map(user,UserWithAllDto.class);

    }

}
