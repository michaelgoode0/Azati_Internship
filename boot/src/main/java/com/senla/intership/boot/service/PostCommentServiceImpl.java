package com.senla.intership.boot.service;

import com.senla.intership.boot.api.repository.PostCommentRepository;
import com.senla.intership.boot.api.repository.PostRepository;
import com.senla.intership.boot.api.service.PostCommentService;
import com.senla.intership.boot.dto.comment.PostCommentWithAllDto;
import com.senla.intership.boot.dto.comment.PostCommentWithPostDto;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.PostComment;
import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.exceptions.ResourceNotFoundException;
import com.senla.intership.boot.api.repository.UserRepository;
import com.senla.intership.boot.util.AuthNameHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostCommentWithPostDto create(PostCommentWithPostDto commentDto) {
        User user = userRepository.getByName(AuthNameHolder.getAuthUsername());
        PostComment postComment = mapper.map(commentDto, PostComment.class);
        Post post = postRepository.getById(commentDto.getPost().getId());
        postComment.setPost(post);
        postComment.setProfile(user.getProfile());
        PostComment response = postCommentRepository.save(postComment);
        return mapper.map(response, PostCommentWithPostDto.class);
    }

    @Override
    @Transactional
    public PostCommentWithPostDto update(PostCommentWithPostDto commentDto) {
        PostComment postComment = postCommentRepository.findById(commentDto.getId())
                .orElseThrow((()->new ResourceNotFoundException("Post comment with id:" + commentDto.getId()+" not found")));
        mapper.map(commentDto,postComment);
        PostComment response = postCommentRepository.save(postComment);
        return mapper.map(response, PostCommentWithPostDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        postCommentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PostCommentWithAllDto read(Long id) {
        PostComment response = postCommentRepository.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("Post comment with id:" + id +" not found")));
        return mapper.map(response, PostCommentWithAllDto.class);
    }

}
