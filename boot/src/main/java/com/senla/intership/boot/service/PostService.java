package com.senla.intership.boot.service;

import com.senla.intership.boot.dto.post.PostDto;
import com.senla.intership.boot.dto.post.PostWithAllDto;
import com.senla.intership.boot.dto.post.PostWithProfileDto;
import com.senla.intership.boot.dto.post.PostWithReactionsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface PostService {
    PostWithReactionsDto setReaction(Long postId, boolean react);

    void delete(Long id);

    PostWithAllDto read(Long id);

    PostDto create(PostDto postDto);

    @PreAuthorize("@postServiceImpl.read(#postDto.id).profile.user.username == authentication.name" +
            "|| hasRole('ROLE_ADMIN')")
    PostWithProfileDto update(PostDto postDto);

    Page<PostWithAllDto> findAll(Pageable pageable);
}
