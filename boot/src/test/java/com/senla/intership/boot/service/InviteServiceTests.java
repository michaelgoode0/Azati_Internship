package com.senla.intership.boot.service;

import com.senla.intership.boot.dto.invite.InviteDto;
import com.senla.intership.boot.entity.Invite;
import com.senla.intership.boot.repository.InviteRepository;
import com.senla.intership.boot.service.impl.InviteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InviteServiceTests {

    @InjectMocks
    private InviteServiceImpl inviteService;

    @Mock
    private InviteRepository inviteRepository;

    @Spy
    private ModelMapper mapper;

    @Test
    public void shouldReadInviteCorrect() {
        Invite invite = new Invite();
        Date date = new Date();
        invite.setDateOfInvite(date);
        invite.setId(123L);

        when(inviteRepository.save(any())).thenReturn(invite);
        when(inviteRepository.findById(any())).thenReturn(Optional.of(invite));

        InviteDto inviteDto = new InviteDto();
        inviteDto.setDateOfInvite(date);
        inviteService.save(inviteDto);
        inviteDto = inviteService.read(123L);

        assertEquals(inviteDto.getId(), 123L);
        assertEquals(inviteDto.getDateOfInvite(), date);
    }

    @Test
    public void shouldSaveInviteCorrect() {
        Invite invite = new Invite();
        Date date = new Date();
        invite.setDateOfInvite(date);
        invite.setId(123L);

        when(inviteRepository.save(any())).thenReturn(invite);

        InviteDto inviteDto = new InviteDto();
        inviteDto.setDateOfInvite(date);
        inviteDto = inviteService.save(inviteDto);

        assertEquals(inviteDto.getId(), 123L);
        assertEquals(inviteDto.getDateOfInvite(), date);
    }
}
