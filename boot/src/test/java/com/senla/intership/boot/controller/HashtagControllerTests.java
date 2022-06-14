package com.senla.intership.boot.controller;


import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.api.repository.HashtagRepository;
import com.senla.intership.boot.entity.Hashtag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
public class HashtagControllerTests extends BootApplicationTests {

    @Autowired
    private HashtagRepository hashtagRepository;

    private Hashtag hashtag;
    private Hashtag hashtag1;


    @BeforeEach
    public void init(){
        hashtag = new Hashtag();
        hashtag.setValue("text");
        hashtag = hashtagRepository.save(hashtag);

        hashtag1 = new Hashtag();
        hashtag1.setValue("text1");
        hashtag1 = hashtagRepository.save(hashtag1);
    }

    @Test
    public void shouldGetALlHashtagsCorrectly() throws Exception {

        mockMvc.perform(
                get("/hashtags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                .principal(authenticationToken))
                .andExpect(jsonPath("$[0].id").value(hashtag.getId()))
                .andExpect(jsonPath("$[1].id").value(hashtag1.getId()))
                .andExpect(jsonPath("$[0].value").value(hashtag.getValue()))
                .andExpect(jsonPath("$[1].value").value(hashtag1.getValue()))
                .andDo(print());
    }
}
