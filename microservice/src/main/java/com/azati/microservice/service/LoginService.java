package com.azati.microservice.service;

import com.azati.microservice.dto.LoginDto;
import com.azati.microservice.dto.UserDto;

public interface LoginService {
    String signIn(LoginDto dto);

    UserDto signUp(LoginDto dto);
}
