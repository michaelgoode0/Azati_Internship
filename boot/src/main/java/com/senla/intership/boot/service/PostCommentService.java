package com.senla.intership.boot.service;

import com.senla.intership.boot.dto.comment.PostCommentWithAllDto;
import com.senla.intership.boot.dto.comment.PostCommentWithPostDto;

public interface PostCommentService {
    PostCommentWithPostDto create(PostCommentWithPostDto commentDto);

    PostCommentWithPostDto update(PostCommentWithPostDto commentDto);

    void delete(Long id);

    PostCommentWithAllDto read(Long id);
}
