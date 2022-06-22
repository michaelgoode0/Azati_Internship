package com.senla.intership.boot.dto.post;


import com.senla.intership.boot.dto.profile.UserProfileWithAllDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostWithProfileDto {
    private Long id;
    @NotEmpty(message = "Post text can not be empty")
    private String text;
    private UserProfileWithAllDto userProfile;
}
