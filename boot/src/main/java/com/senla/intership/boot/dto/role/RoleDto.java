package com.senla.intership.boot.dto.role;

import com.senla.intership.boot.enums.RoleName;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleName roleName;
}
