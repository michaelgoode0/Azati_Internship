package com.senla.intership.boot.dto.profile;

import com.senla.intership.boot.dto.user.UserDto;
import lombok.Data;

@Data
public class UserProfileWithUserDto {
    private Long id;
    private String firstname;
    private String surname;
    private String town;
    private Long phoneNumber;
    private UserDto user;
}
