package com.azati.microservice.dto;

import com.azati.microservice.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String password;
    private List<Role> roles;
}
