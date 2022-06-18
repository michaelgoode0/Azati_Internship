package com.azati.microservice.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private Long id;
    private String username;
    @JsonUnwrapped
    private List<RoleDto> roles;
}
