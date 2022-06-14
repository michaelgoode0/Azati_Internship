package com.senla.intership.boot.repository;

import com.senla.intership.boot.BootApplication;
import com.senla.intership.boot.api.repository.UserProfileRepository;
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
public class UserProfileRepositoryTests {
    @Autowired
    private UserProfileRepository profileRepository;

    private UserProfile firstProfile;
    private UserProfile secondProfile;

    private final static String firstName = "Artyom";
    private final static String secondName = "Ilya";
    @BeforeEach
    public void init() {
        if (profileRepository.findAll().size() == 0) {
            firstProfile = new UserProfile();
            firstProfile.setFirstname(firstName);
            firstProfile = profileRepository.save(firstProfile);

            secondProfile = new UserProfile();
            secondProfile.setFirstname(secondName);
            secondProfile = profileRepository.save(secondProfile);
        } else {
            firstProfile = profileRepository.getById(1L);
            secondProfile = profileRepository.getById(2L);
        }
    }

    @Test
    public void hibernateShouldSetIdWhenHashtagSaved() {
        assertNotNull(firstProfile.getId());
        assertNotNull(secondProfile.getId());
    }

    @Test
    public void shouldFindHashtagByIdCorrect() {
        final UserProfile potentialRole = profileRepository.findById(firstProfile.getId()).orElse(new UserProfile());
        final UserProfile  potentialRole2 = profileRepository.findById(secondProfile.getId()).orElse(new UserProfile());
        assertEquals(firstProfile.getId(), potentialRole.getId());
        assertEquals(secondProfile.getId(), potentialRole2.getId());
    }

    @Test
    public void hibernateShouldUpdateHashtag() {
        String name = "Kirill";
        firstProfile.setFirstname(name);
        profileRepository.save(firstProfile);

        assertEquals(firstProfile.getFirstname(), name);
    }

}
