package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.api.repository.PostCommentRepository;
import com.senla.intership.boot.api.repository.PostRepository;
import com.senla.intership.boot.api.repository.UserProfileRepository;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.PostComment;
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
public class PostCommentRepositoryTests {
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PostRepository postRepository;

    private PostComment firstComment;
    private PostComment secondComment;
    private static final String firstText = "hye";
    private static final String secondText = "hello";
    @BeforeEach
    public void init() {
        if (postCommentRepository.findAll().size() == 0) {
            UserProfile userProfile = new UserProfile();
            userProfileRepository.save(userProfile);
            Post post = new Post();
            post.setProfile(userProfile);
            postRepository.save(post);



            firstComment = new PostComment();
            firstComment.setProfile(userProfile);
            firstComment.setText(firstText);
            firstComment.setPost(post);
            firstComment = postCommentRepository.save(firstComment);

            UserProfile userProfile1 = new UserProfile();
            userProfileRepository.save(userProfile1);

            Post post1 = new Post();
            post1.setProfile(userProfile1);

            postRepository.save(post1);



            secondComment = new PostComment();
            secondComment.setProfile(userProfile1);
            secondComment.setText(secondText);
            secondComment.setPost(post1);
            secondComment = postCommentRepository.save(secondComment);
        } else {
            firstComment = postCommentRepository.getById(1L);
            secondComment = postCommentRepository.getById(2L);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(firstComment.getId());
        assertNotNull(secondComment.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final PostComment potentialComment = postCommentRepository.findById(firstComment.getId()).orElse(new PostComment());
        final PostComment potentialComment2 = postCommentRepository.findById(secondComment.getId()).orElse(new PostComment());
        assertEquals(firstComment.getId(), potentialComment.getId());
        assertEquals(secondComment.getId(), potentialComment2.getId());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        String text = "text";
        firstComment.setText(text);
        postCommentRepository.save(firstComment);

        assertEquals(firstComment.getText(), text);
    }
}
