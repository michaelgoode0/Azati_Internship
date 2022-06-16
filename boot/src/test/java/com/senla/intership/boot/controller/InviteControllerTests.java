package com.senla.intership.boot.controller;

import com.senla.intership.boot.BootApplicationTests;
import com.senla.intership.boot.entity.Invite;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.repository.InviteRepository;
import com.senla.intership.boot.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
public class InviteControllerTests extends BootApplicationTests {

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private Invite invite;
    private Invite invite1;

    private UserProfile userProfile;
    private UserProfile userProfile1;

    @BeforeEach
    public void init() {
        userProfile = new UserProfile();
        userProfile.setFirstname("Artyom");
        userProfile = userProfileRepository.save(userProfile);

        userProfile1 = new UserProfile();
        userProfile1.setFirstname("Ilya");
        userProfile1 = userProfileRepository.save(userProfile1);


        invite = new Invite();
        invite.setDateOfInvite(new Date());
        invite.setUserFrom(userProfile);
        invite.setUserTo(userProfile1);
        inviteRepository.save(invite);

        invite1 = new Invite();
        invite1.setDateOfInvite(new Date());
        invite1.setUserFrom(userProfile1);
        invite1.setUserTo(userProfile);
        inviteRepository.save(invite1);

    }

    @Test
    public void shouldGetAllInvitesCorrectly() throws Exception {

        mockMvc.perform(
                get("/invites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(authenticationToken))
                .andExpect(jsonPath("$[0].id").value(invite.getId()))
                .andExpect(jsonPath("$[1].id").value(invite1.getId()))
                .andExpect(jsonPath("$[0].dateOfInvite").value(invite.getDateOfInvite()))
                .andExpect(jsonPath("$[1].dateOfInvite").value(invite1.getDateOfInvite()))
                .andDo(print());
    }

}
