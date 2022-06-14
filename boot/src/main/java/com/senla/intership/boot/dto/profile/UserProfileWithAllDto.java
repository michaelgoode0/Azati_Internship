package com.senla.intership.boot.dto.profile;

import com.senla.intership.boot.dto.invite.InviteDto;
import com.senla.intership.boot.dto.post.PostDto;
import com.senla.intership.boot.dto.reaction.ReactionDto;
import com.senla.intership.boot.dto.user.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileWithAllDto {
    private Long id;
    private String firstname;
    private String surname;
    private String town;
    private Long phoneNumber;
    private List<PostDto> posts;
    private List<ReactionDto> reactions;
    private List<UserProfileDto> friends;
    private List<InviteDto> invites;
    private UserDto user;
}
