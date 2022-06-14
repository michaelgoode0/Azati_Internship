package com.senla.intership.boot.dto.post;


import com.senla.intership.boot.dto.profile.UserProfileWithAllDto;
import lombok.Data;

@Data
public class PostWithProfileDto {
    private Long id;
    private String text;
    private UserProfileWithAllDto userProfile;
}
