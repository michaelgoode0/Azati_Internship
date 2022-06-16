package com.senla.intership.boot.controller;

import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.Reaction;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.repository.PostRepository;
import com.senla.intership.boot.repository.ReactionRepository;
import com.senla.intership.boot.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
public class ReactionControllerTests extends BootApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    private Reaction firstReaction;
    private Reaction secondReaction;

    @BeforeEach
    public void init() {
        UserProfile userProfile = new UserProfile();
        userProfileRepository.save(userProfile);

        Post firstPost = new Post();
        firstPost.setProfile(userProfile);
        postRepository.save(firstPost);

        firstReaction = new Reaction();
        firstReaction.setPost(firstPost);
        firstReaction.setProfile(userProfile);
        reactionRepository.save(firstReaction);

        UserProfile userProfile1 = new UserProfile();
        userProfileRepository.save(userProfile1);

        secondReaction = new Reaction();
        secondReaction.setProfile(userProfile1);
        secondReaction.setPost(firstPost);
        reactionRepository.save(secondReaction);
    }

    @Test
    public void shouldGetAllHashtagsCorrectly() throws Exception {
        mockMvc.perform(
                get("/reactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(firstReaction.getId()))
                .andExpect(jsonPath("$[1].id").value(secondReaction.getId()))
                .andExpect(jsonPath("$[0].reaction").value(firstReaction.getReaction()))
                .andExpect(jsonPath("$[1].reaction").value(secondReaction.getReaction()));

    }
}
