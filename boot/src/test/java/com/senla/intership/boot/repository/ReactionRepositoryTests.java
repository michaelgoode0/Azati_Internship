package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.api.repository.PostRepository;
import com.senla.intership.boot.api.repository.ReactionRepository;
import com.senla.intership.boot.api.repository.UserProfileRepository;
import com.senla.intership.boot.entity.Post;
import com.senla.intership.boot.entity.Reaction;
import com.senla.intership.boot.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = BootApplication.class)
public class ReactionRepositoryTests {
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
        if (postRepository.findAll().size() == 0) {
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

            Post secondPost = new Post();
            secondPost.setProfile(userProfile1);
            postRepository.save(secondPost);

            secondReaction = new Reaction();
            secondReaction.setProfile(userProfile1);
            secondReaction.setPost(firstPost);
            reactionRepository.save(secondReaction);
        } else {
            firstReaction = reactionRepository.getById(1L);
            secondReaction = reactionRepository.getById(2L);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(firstReaction.getId());
        assertNotNull(secondReaction.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final Reaction potentialReaction = reactionRepository.findById(firstReaction.getId()).orElse(new Reaction());
        final Reaction potentialReaction2 = reactionRepository.findById(secondReaction.getId()).orElse(new Reaction());
        assertEquals(firstReaction.getId(), potentialReaction.getId());
        assertEquals(secondReaction.getId(), potentialReaction2.getId());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        firstReaction.setReaction(true);
        reactionRepository.save(firstReaction);

        assertEquals(firstReaction.getReaction(), true);
    }

    @Test
    public void shouldFindAllCorrect(){
        List<Reaction> listOfReactions = reactionRepository.findAll();
        assertTrue(listOfReactions.contains(firstReaction));
        assertTrue(listOfReactions.contains(secondReaction));
        assertEquals(listOfReactions.size(),2);
    }

  /*  @Test
    public void shouldReturnAllReactionsByPostWithPagination() {
        Pageable pageable = PageRequest.of(0, 1);

        assertEquals(postRepository.findAll().size(), 2);
        Page<Reaction> reactions = reactionRepository.findAllByPost(pageable, 1L);

        assertEquals(reactions.getContent().get(0).getReaction(),true);
    }*/
}
