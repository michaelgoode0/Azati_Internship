package com.senla.intership.boot.api.service;

import com.senla.intership.boot.dto.user.LoginDto;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.security.enums.RoleName;

public interface UserService {
    UserWithAllDto signUp(LoginDto dto, RoleName roleName);
    String signIn(LoginDto dto);
    void delete(Long id);
    UserWithAllDto loadByUsername(String username);
}
