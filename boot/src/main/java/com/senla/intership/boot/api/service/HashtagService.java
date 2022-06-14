package com.senla.intership.boot.api.service;

import com.senla.intership.boot.dto.hashtag.HashtagWithPostsDto;
import com.senla.intership.boot.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface HashtagService  {
    Page<HashtagWithPostsDto> getAll (Pageable pageable);
    Page<HashtagWithPostsDto> getAllTop(Pageable pageable);
    HashtagWithPostsDto save(HashtagWithPostsDto hashtagDto);
    Set<HashtagWithPostsDto> createUniqueHashtags(PostDto postDto);
    void delete(Long id);
    HashtagWithPostsDto read(Long id);
}
