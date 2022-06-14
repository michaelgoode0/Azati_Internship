package com.senla.intership.boot.service;

import com.senla.intership.boot.api.repository.InviteRepository;
import com.senla.intership.boot.api.service.InviteService;
import com.senla.intership.boot.api.service.UserProfileService;
import com.senla.intership.boot.dto.invite.InviteDto;
import com.senla.intership.boot.dto.profile.UserProfileWithAllDto;
import com.senla.intership.boot.entity.Invite;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.enums.InviteStatus;
import com.senla.intership.boot.exceptions.ResourceNotFoundException;
import com.senla.intership.boot.api.service.UserService;
import com.senla.intership.boot.dto.user.UserWithAllDto;
import com.senla.intership.boot.util.AuthNameHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final ModelMapper mapper;

    @Override
   /* @PreAuthorize("hasRole('ROLE_ADMIN')")*/
    public Page<InviteDto> findAll(Pageable pageable) {
        return inviteRepository.findAll(pageable).map(entity->mapper.map(entity, InviteDto.class));
    }

    @Override
    public Page<InviteDto> findInviteByStatus(InviteStatus status,Pageable pageable) {
        return inviteRepository.findAllByStatus(status,pageable)
                .map(entity->mapper.map(entity, InviteDto.class));
    }

    @Override
    @Transactional
    public InviteDto sendInvite(Long toUserId){
            UserWithAllDto user =  userService.loadByUsername(AuthNameHolder.getAuthUsername());
            UserProfile fromUser = mapper.map(user.getProfile(),UserProfile.class);
            UserProfileWithAllDto userProfile = userProfileService.read(toUserId);
            UserProfile toUser = mapper.map(userProfile,UserProfile.class);
            if(inviteRepository.findInviteByUserToAndUserFrom(toUser,fromUser)==null) {
                Invite inv = new Invite();
                inv.setUserFrom(fromUser);
                inv.setUserTo(toUser);
                inv.setDateOfInvite(new Date());
                inv.setStatus(InviteStatus.WAITING);
                Invite invite = inviteRepository.save(inv);
                return mapper.map(invite, InviteDto.class);
            }
            else{
                throw new ResourceNotFoundException("User can not be invited");
            }
    }

    @Override
    @Transactional
    @PreAuthorize("@inviteServiceImpl.read(#id).userTo.user.username==authentication.name" + "|| hasRole('ROLE_ADMIN')")
    public InviteDto acceptInvite(Long id){
        Invite invite= inviteRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invite object with id:" + id + " not found"));
        invite.setStatus(InviteStatus.ACCEPTED);
        return mapper.map(invite,InviteDto.class);
    }

    @Override
    @Transactional
    @PreAuthorize("@inviteServiceImpl.read(#id).userTo.user.username==authentication.name"+ "|| hasRole('ROLE_ADMIN')")
    public InviteDto denyInvite(Long id){
        Invite invite= inviteRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invite object with id:" + id + " not found"));
        invite.setStatus(InviteStatus.DENIED);
        return mapper.map(invite,InviteDto.class);
    }

    @Override
    @Transactional
    public InviteDto read(Long id){
        Invite invite = inviteRepository.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("Invite object with id:" + id + " not found")));
        return mapper.map(invite,InviteDto.class);
    }

    @Override
    public InviteDto save(InviteDto inviteDto) {
        Invite invite = mapper.map(inviteDto,Invite.class);
        Invite response = inviteRepository.save(invite);
        return mapper.map(response,InviteDto.class);
    }
}
