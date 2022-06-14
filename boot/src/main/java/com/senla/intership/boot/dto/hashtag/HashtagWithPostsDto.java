package com.senla.intership.boot.dto.hashtag;


import com.senla.intership.boot.dto.post.PostDto;
import lombok.Data;

import java.util.List;

@Data
public class HashtagWithPostsDto {
    private Long id;
    private String value;
    private List<PostDto> posts;
}
