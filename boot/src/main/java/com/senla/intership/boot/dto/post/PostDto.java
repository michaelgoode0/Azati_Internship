package com.senla.intership.boot.dto.post;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {
    private Long id;
    @NotEmpty(message = "Post text can not be empty")
    private String text;
}
