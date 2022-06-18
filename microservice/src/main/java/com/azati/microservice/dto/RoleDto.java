package com.azati.microservice.dto;

import com.azati.microservice.model.RoleName;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private Long id;
    private RoleName roleName;
}
