package com.azati.microservice.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    @JsonUnwrapped
    private List<RoleDto> roles;
}
