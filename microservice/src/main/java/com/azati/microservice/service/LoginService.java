package com.azati.microservice.service;

import com.azati.microservice.dto.LoginDto;
import com.azati.microservice.dto.UserDto;
import com.azati.microservice.model.RoleName;

public interface LoginService {
    String signIn(LoginDto dto);

    UserDto signUp(LoginDto dto);
}
