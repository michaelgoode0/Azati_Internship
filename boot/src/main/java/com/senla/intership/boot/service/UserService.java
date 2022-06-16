package com.senla.intership.boot.service;

import com.senla.intership.boot.dto.user.LoginDto;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.dto.user.UserWithRolesDto;

public interface UserService {
    UserWithRolesDto signUp(LoginDto dto);

    String signIn(LoginDto dto);

    void delete(Long id);

    UserWithAllDto loadByUsername(String username);
}
