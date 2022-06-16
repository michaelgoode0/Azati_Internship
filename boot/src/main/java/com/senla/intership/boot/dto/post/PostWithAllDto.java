package com.senla.intership.boot.dto.post;

import com.senla.intership.boot.dto.comment.PostCommentDto;
import com.senla.intership.boot.dto.profile.UserProfileWithUserDto;
import com.senla.intership.boot.dto.reaction.ReactionDto;
import lombok.Data;

import java.util.List;

@Data
public class PostWithAllDto {
    private Long id;
    private String text;
    private UserProfileWithUserDto profile;
    private List<PostCommentDto> comments;
    private List<ReactionDto> reactions;
}
