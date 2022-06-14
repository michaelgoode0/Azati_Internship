package com.senla.intership.boot.controller;

import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.api.repository.PostCommentRepository;
import com.senla.intership.boot.api.repository.PostRepository;
import com.senla.intership.boot.api.repository.UserProfileRepository;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.PostComment;
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
public class PostCommentControllerTests extends BootApplicationTests {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PostRepository postRepository;

    private PostComment firstComment;
    private static final String firstText = "hye";
    private Post post;

    @BeforeEach
    public void init() {
        UserProfile userProfile = new UserProfile();
        userProfileRepository.save(userProfile);
        post = new Post();
        post.setProfile(userProfile);
        postRepository.save(post);

        firstComment = new PostComment();
        firstComment.setProfile(userProfile);
        firstComment.setText(firstText);
        firstComment.setPost(post);
        firstComment = postCommentRepository.save(firstComment);


    }

    @Test
    public void shouldCreateCommentCorrect() throws Exception {
        String postCommentDto = String.format(
                """
                        {
                             "text": "Creation",
                             "post": { 
                                      "id": %s
                                     }
                        }
                        """, post.getId());
        mockMvc.perform(
                post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postCommentDto)
                        .principal(authenticationToken))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").value("Creation"))
                .andDo(print());
    }

    @Test
    public void shouldReadCommentCorrect() throws Exception {
        String postCommentDto = String.format(
                """
                        {
                             "text": "Creation",
                             "post": { 
                                      "id": %s
                                     }
                        }
                        """, post.getId());
        mockMvc.perform(
                get("/comments/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postCommentDto)
                        .principal(authenticationToken))
                .andExpect(jsonPath("$.id").value(firstComment.getId()))
                .andExpect(jsonPath("$.text").value(firstText))
                .andDo(print());
    }

    @Test
    public void shouldUpdateCommentCorrect() throws Exception {
        String postCommentDto = String.format(
                """
                        {
                             "id": %s,
                             "text": "text",
                             "post": { 
                                      "id": %s
                                     }
                        }
                        """, firstComment.getId(), post.getId());
        mockMvc.perform(
                put("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postCommentDto)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(firstComment.getId()))
                .andExpect(jsonPath("$.text").value("text"));

    }

    @Test
    public void shouldDeleteCommentCorrectly() throws Exception {
        mockMvc.perform(
                delete("/comments/{id}", firstComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andExpect(status().is2xxSuccessful());
    }


}
