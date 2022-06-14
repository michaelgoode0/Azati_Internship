package com.senla.intership.boot.controller;

import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.api.repository.PostRepository;
import com.senla.intership.boot.api.repository.UserProfileRepository;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PostControllerTests extends BootApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private Post firstPost;
    private Post secondPost;
    private static final String firstText = "hye";
    private static final String secondText = "hello";

    @BeforeEach
    public void init() {
        UserProfile userProfile = new UserProfile();
        userProfileRepository.save(userProfile);
        firstPost = new Post();
        firstPost.setText(firstText);
        firstPost.setProfile(userProfile);
        firstPost = postRepository.save(firstPost);
        UserProfile userProfile1 = new UserProfile();
        userProfileRepository.save(userProfile1);
        secondPost = new Post();
        secondPost.setText(secondText);
        secondPost.setProfile(userProfile1);
        secondPost = postRepository.save(secondPost);
    }


    @Test
    public void shouldCreateCommentCorrect() throws Exception {
        String postDto = """
                {
                     "text": "Creation"
                }
                """;
        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postDto)
                        .principal(authenticationToken))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").value("Creation"))
                .andDo(print());
    }

    @Test
    public void shouldReadCommentCorrect() throws Exception {
        mockMvc.perform(
                get("/posts/{id}", firstPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andExpect(jsonPath("$.id").value(firstPost.getId()))
                .andExpect(jsonPath("$.text").value(firstText))
                .andDo(print());
    }

    @Test
    public void shouldUpdateCommentCorrect() throws Exception {
        String postDto =String.format(
                """
                {
                     "id": %s,
                     "text": "text"
                }
                """,firstPost.getId());
        mockMvc.perform(
                put("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postDto)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(firstPost.getId()))
                .andExpect(jsonPath("$.text").value("text"));

    }

    @Test
    public void shouldDeleteCommentCorrect() throws Exception {
        mockMvc.perform(
                delete("/posts/{id}",firstPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldGetAllHashtagsCorrectly() throws Exception {
        mockMvc.perform(
                get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(firstPost.getId()))
                .andExpect(jsonPath("$[1].id").value(secondPost.getId()))
                .andExpect(jsonPath("$[0].text").value(firstPost.getText()))
                .andExpect(jsonPath("$[1].text").value(secondPost.getText()));

    }
   /* @Test
    public void shouldLikePostCorrectly() throws Exception {
        mockMvc.perform(
                get("/posts/{id}/like",firstPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(firstPost.getId()))
                .andExpect(jsonPath("$.text").value(firstPost.getText()));
    }*/



}

