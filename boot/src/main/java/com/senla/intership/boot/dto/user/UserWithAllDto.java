package com.senla.intership.boot.dto.user;

import com.senla.intership.boot.dto.profile.UserProfileDto;
import com.senla.intership.boot.dto.role.RoleDto;
import lombok.Data;

import java.util.List;
@Data
public class UserWithAllDto {
    private Long id;
    private String username;
    private List<RoleDto> roles;
    private UserProfileDto profile;
}
