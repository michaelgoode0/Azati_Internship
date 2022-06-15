package com.azati.microservice.dto;

import com.azati.microservice.model.RoleName;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleName roleName;
}
