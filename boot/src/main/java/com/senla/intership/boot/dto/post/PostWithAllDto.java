package com.senla.intership.boot.dto.post;

import com.senla.intership.boot.dto.comment.PostCommentDto;
import com.senla.intership.boot.dto.profile.UserProfileWithUserDto;
import com.senla.intership.boot.dto.reaction.ReactionDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PostWithAllDto {
    private Long id;
    @NotEmpty(message = "Post text can not be empty")
    private String text;
    private UserProfileWithUserDto profile;
    private List<PostCommentDto> comments;
    private List<ReactionDto> reactions;
}
