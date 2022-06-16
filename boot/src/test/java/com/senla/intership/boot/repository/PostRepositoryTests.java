package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {BootApplication.class})
@DataJpaTest
public class PostRepositoryTests {
    private static final String firstText = "hye";
    private static final String secondText = "hello";
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    private Post firstPost;
    private Post secondPost;

    @BeforeEach
    public void init() {
        if (postRepository.findAll().size() == 0) {
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
        } else {
            firstPost = postRepository.getById(1L);
            secondPost = postRepository.getById(2L);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(firstPost.getId());
        assertNotNull(secondPost.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final Post potentialComment = postRepository.findById(firstPost.getId()).orElse(new Post());
        final Post potentialComment2 = postRepository.findById(secondPost.getId()).orElse(new Post());
        assertEquals(firstPost.getId(), potentialComment.getId());
        assertEquals(secondPost.getId(), potentialComment2.getId());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        String text = "text";
        firstPost.setText(text);
        postRepository.save(firstPost);

        assertEquals(firstPost.getText(), text);
    }
}
