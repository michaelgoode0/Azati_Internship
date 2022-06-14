package com.senla.intership.boot.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostCommentDto {
    private Long id;
    @NotEmpty(message = "Post comment can not be empty")
    private String text;
}
