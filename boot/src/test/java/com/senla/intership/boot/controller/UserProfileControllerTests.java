package com.senla.intership.boot.controller;

import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.repository.UserProfileRepository;
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
public class UserProfileControllerTests extends BootApplicationTests {
    private final static String firstName = "Artyom";
    private final static String secondName = "Ilya";
    @Autowired
    private UserProfileRepository profileRepository;
    private UserProfile firstProfile;
    private UserProfile secondProfile;

    @BeforeEach
    public void init() {
        firstProfile = new UserProfile();
        firstProfile.setFirstname(firstName);
        firstProfile = profileRepository.save(firstProfile);

        secondProfile = new UserProfile();
        secondProfile.setFirstname(secondName);
        secondProfile = profileRepository.save(secondProfile);
    }

    @Test
    public void shouldReadCommentCorrect() throws Exception {
        mockMvc.perform(
                get("/profiles/{id}", firstProfile.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andExpect(jsonPath("$.id").value(firstProfile.getId()))
                .andExpect(jsonPath("$.firstname").value(firstName))
                .andDo(print());
    }

    @Test
    public void shouldUpdateCommentCorrect() throws Exception {
        String postDto = String.format(
                """
                        {
                             "id": %s,
                             "firstname": "Egor"
                        }
                        """, firstProfile.getId());
        mockMvc.perform(
                put("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(postDto)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(firstProfile.getId()))
                .andExpect(jsonPath("$.firstname").value("Egor"));

    }

    @Test
    public void shouldDeleteCommentCorrect() throws Exception {
        mockMvc.perform(
                delete("/profiles/{id}", firstProfile.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldGetAllProfilesCorrect() throws Exception {
        mockMvc.perform(
                get("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(userProfile.getId()))
                .andExpect(jsonPath("$[1].id").value(firstProfile.getId()))
                .andExpect(jsonPath("$[2].id").value(secondProfile.getId()))
                .andExpect(jsonPath("$[0].firstname").value(userProfile.getFirstname()))
                .andExpect(jsonPath("$[1].firstname").value(firstProfile.getFirstname()))
                .andExpect(jsonPath("$[2].firstname").value(secondProfile.getFirstname()));
    }
}
