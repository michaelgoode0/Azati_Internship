package com.senla.intership.boot.dto.comment;

import com.senla.intership.boot.dto.post.PostDto;
import com.senla.intership.boot.dto.profile.UserProfileWithUserDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostCommentWithAllDto {
    private Long id;
    @NotEmpty(message = "Post comment can not be empty")
    private String text;
    private UserProfileWithUserDto profile;
    private PostDto post;
}
