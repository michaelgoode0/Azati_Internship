package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.api.repository.HashtagRepository;
import com.senla.intership.boot.entity.Hashtag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {BootApplication.class})
@DataJpaTest
public class HashtagRepositoryTests {

    @Autowired
    private HashtagRepository hashtagRepository;

    private Hashtag firstHashtag;
    private Hashtag secondHashtag;
    private static final String firstValue = "#hash";
    private static final String  secondValue = "#hash2";

    @BeforeEach
    public void init() {
        if (hashtagRepository.findAll().size() == 0) {
            firstHashtag= new Hashtag();
            firstHashtag.setValue(firstValue);
            firstHashtag = hashtagRepository.save(firstHashtag);
            secondHashtag = new Hashtag();
            secondHashtag.setValue(secondValue);
            secondHashtag = hashtagRepository.save(secondHashtag);
        } else {
            firstHashtag = hashtagRepository.findHashtagByValue(firstValue);
            secondHashtag = hashtagRepository.findHashtagByValue(secondValue);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(firstHashtag.getId());
        assertNotNull(secondHashtag.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final Hashtag potentialHashtag = hashtagRepository.findById(firstHashtag.getId()).orElse(new Hashtag());
        final Hashtag potentialHashtag2 = hashtagRepository.findById(secondHashtag.getId()).orElse(new Hashtag());
        assertEquals(firstHashtag.getId(), potentialHashtag.getId());
        assertEquals(secondHashtag.getId(), potentialHashtag2.getId());
    }

    @Test
    public void shouldFindHashtagByValueCorrect() {

        final Hashtag potentialHashtag = hashtagRepository.findHashtagByValue(firstHashtag.getValue());

        assertEquals(firstHashtag.getId(), potentialHashtag.getId());
        assertEquals(firstHashtag.getValue(), potentialHashtag.getValue());

        final Hashtag potentialHashtag2 = hashtagRepository.findHashtagByValue(secondHashtag.getValue());

        assertEquals(secondHashtag.getId(), potentialHashtag2.getId());
        assertEquals(secondHashtag.getValue(), potentialHashtag2.getValue());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        String hashtagName = "#3";
        firstHashtag.setValue(hashtagName);
        hashtagRepository.save(firstHashtag);

        assertEquals(firstHashtag.getValue(), hashtagName);
    }

    @Test
    public void shouldFindListOfHashtagValueCorrect() {
        List<String> hashtagValues= hashtagRepository.findHashtagValues();
        assertTrue(hashtagValues.contains(firstValue));
        assertTrue(hashtagValues.contains(secondValue));
    }

    @Test
    public void shouldReturnAllHashtagsWithPagination() {
        Pageable pageable = PageRequest.of(0,2);
        Page<Hashtag> hashtags = hashtagRepository.getAllTop(pageable);
        assertEquals(hashtags.getContent().get(1).getValue(), firstValue);
        assertEquals(hashtags.getContent().get(0).getValue(), secondValue);
    }
}



