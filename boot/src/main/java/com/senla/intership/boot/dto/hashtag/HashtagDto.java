package com.senla.intership.boot.dto.hashtag;

import com.senla.intership.boot.dto.post.PostDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class HashtagDto {
    private Long id;
    @NotEmpty(message = "Hashtag value can not be empty")
    private String value;
    private List<PostDto> posts;
}
