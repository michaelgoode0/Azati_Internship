package com.senla.intership.boot.dto.reaction;

import com.senla.intership.boot.dto.profile.UserProfileDto;
import lombok.Data;

@Data
public class ReactionWithProfileDto {
    private Long id;
    private Boolean reaction;
    private UserProfileDto profile;
}
