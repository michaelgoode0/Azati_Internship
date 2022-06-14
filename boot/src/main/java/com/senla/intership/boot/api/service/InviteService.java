package com.senla.intership.boot.api.service;

import com.senla.intership.boot.dto.invite.InviteDto;
import com.senla.intership.boot.enums.InviteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InviteService {
    InviteDto sendInvite(Long toUserId);
    InviteDto acceptInvite(Long toUserId);
    InviteDto read(Long id);
    InviteDto save(InviteDto inviteDto);
    InviteDto denyInvite(Long id);
    Page<InviteDto> findAll(Pageable pageable);
    Page<InviteDto> findInviteByStatus(InviteStatus status,Pageable pageable);
}
