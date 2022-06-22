package com.senla.intership.boot.dto.profile;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class UserProfileDto {
    private Long id;
    @NotEmpty(message = "Profiles firstname can not be empty")
    private String firstname;
    @NotEmpty(message = "Profiles surname name can not be empty")
    private String surname;
    @NotEmpty(message = "Profile's town can not be empty")
    private String town;
    @Min(5)
    private Long phoneNumber;
}
