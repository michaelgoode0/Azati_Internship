package com.senla.intership.boot.dto.reaction;

import com.senla.intership.boot.dto.profile.UserProfileDto;
import com.senla.intership.boot.dto.post.PostDto;
import lombok.Data;

@Data
public class ReactionWithAllDto {
    private Long id;
    private Boolean reaction;
    private UserProfileDto profile;
    private PostDto post;
}
