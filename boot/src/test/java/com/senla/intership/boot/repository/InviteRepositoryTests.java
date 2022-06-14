package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.api.repository.InviteRepository;
import com.senla.intership.boot.api.repository.UserProfileRepository;
import com.senla.intership.boot.entity.Invite;
import com.senla.intership.boot.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ContextConfiguration(classes = {BootApplication.class})
@DataJpaTest
public class InviteRepositoryTests {
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;

    private Invite firstInvite;
    private Invite secondInvite;

    @BeforeEach
    public void init() {
        if (inviteRepository.findAll().size() == 0) {

            firstInvite = new Invite();
            firstInvite.setDateOfInvite(new Date());

            UserProfile toUser = new UserProfile();
            UserProfile fromUser = new UserProfile();

            userProfileRepository.save(toUser);
            userProfileRepository.save(fromUser);

            firstInvite.setUserFrom(fromUser);
            firstInvite.setUserTo(toUser);
            firstInvite = inviteRepository.save(firstInvite);

            secondInvite = new Invite();
            UserProfile toUser1 = new UserProfile();
            UserProfile fromUser1 = new UserProfile();

            userProfileRepository.save(toUser1);
            userProfileRepository.save(fromUser1);

            secondInvite.setUserFrom(fromUser1);
            secondInvite.setUserTo(toUser1);
            secondInvite.setDateOfInvite(new Date());
            secondInvite = inviteRepository.save(secondInvite);
        } else {
            firstInvite = inviteRepository.getById(1L);
            secondInvite = inviteRepository.getById(2L);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(firstInvite.getId());
        assertNotNull(secondInvite.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final Invite potentialInvite = inviteRepository.findById(firstInvite.getId()).orElse(new Invite());
        final Invite potentialInvite2 = inviteRepository.findById(secondInvite.getId()).orElse(new Invite());
        assertEquals(firstInvite.getId(), potentialInvite.getId());
        assertEquals(secondInvite.getId(), potentialInvite2.getId());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        Date date = new Date();
        firstInvite.setDateOfInvite(date);
        inviteRepository.save(firstInvite);

        assertEquals(firstInvite.getDateOfInvite(), date);
    }

    @Test
    public void shouldFindInviteByUserToAndUserFromCorrect(){

        final Invite potentialInvite = inviteRepository.findInviteByUserToAndUserFrom(firstInvite.getUserTo(), firstInvite.getUserFrom());

        assertEquals(firstInvite,potentialInvite);
    }

}
