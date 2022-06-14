package com.senla.intership.boot.dto.role;

import com.senla.intership.boot.security.enums.RoleName;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleName roleName;
}
