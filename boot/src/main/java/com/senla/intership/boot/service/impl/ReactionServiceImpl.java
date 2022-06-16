package com.senla.intership.boot.service.impl;

import com.senla.intership.boot.dto.post.PostDto;
import com.senla.intership.boot.dto.reaction.ReactionDto;
import com.senla.intership.boot.dto.reaction.ReactionWithProfileDto;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.Reaction;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.repository.PostRepository;
import com.senla.intership.boot.repository.ReactionRepository;
import com.senla.intership.boot.service.ReactionService;
import com.senla.intership.boot.service.UserService;
import com.senla.intership.boot.util.AuthNameHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public List<ReactionDto> getAll() {
        return reactionRepository.findAll().stream()
                .map(entity -> mapper.map(entity, ReactionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Page<ReactionWithProfileDto> findAllByPost(Pageable pageable, Long id) {
        return reactionRepository.findAll(pageable)
                .map(entity -> mapper.map(entity, ReactionWithProfileDto.class));
    }


    @Override
    @Transactional
    public void react(PostDto postDto, boolean react) {
        UserWithAllDto user = userService.loadByUsername(AuthNameHolder.getAuthUsername());
        UserProfile userProfile = mapper.map(user.getProfile(), UserProfile.class);
        Post post = mapper.map(postDto, Post.class);
        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setReaction(react);
        reaction.setProfile(userProfile);
        Reaction postReaction = reactionRepository.findAll().stream()
                .filter(k -> k.getPost().getId().equals(post.getId()) && k.getProfile().getId().equals(userProfile.getId()))
                .findFirst().orElse(null);
        if (postReaction != null) {
            Boolean isLike = postReaction.getReaction();
            reactionRepository.deleteById(postReaction.getId());
            if (!isLike.equals(react)) {
                reactionRepository.save(reaction);
            }
        } else {
            reactionRepository.save(reaction);
        }
    }

}
