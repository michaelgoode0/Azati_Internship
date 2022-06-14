package com.senla.intership.boot.dto.post;

import com.senla.intership.boot.dto.reaction.ReactionDto;
import lombok.Data;

import java.util.List;

@Data
public class PostWithReactionsDto {
    private Long id;
    private String text;
    private List<ReactionDto> reactions;
}
