package com.senla.intership.boot.service.impl;

import com.senla.intership.boot.dto.profile.UserProfileDto;
import com.senla.intership.boot.dto.profile.UserProfileWithAllDto;
import com.senla.intership.boot.entity.Invite;
import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.enums.InviteStatus;
import com.senla.intership.boot.exceptions.custom.AuthException;
import com.senla.intership.boot.exceptions.custom.ResourceNotFoundException;
import com.senla.intership.boot.repository.InviteRepository;
import com.senla.intership.boot.repository.UserProfileRepository;
import com.senla.intership.boot.repository.UserRepository;
import com.senla.intership.boot.service.UserProfileService;
import com.senla.intership.boot.util.AuthNameHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserProfileDto update(UserProfileDto userProfileDto) {
        User user = userRepository.getByName(AuthNameHolder.getAuthUsername());
        UserProfile userProfile = userProfileRepository.findById(userProfileDto.getId())
                .orElseThrow((() -> new ResourceNotFoundException("Profile with id:" + userProfileDto.getId() + " not found")));
        mapper.map(userProfileDto, userProfile);
        userProfile.setUser(user);
        UserProfile response = userProfileRepository.save(userProfile);
        return mapper.map(response, UserProfileDto.class);
    }

    @Override
    @Transactional
    public UserProfileWithAllDto read(Long id) {
        UserProfile response = userProfileRepository.findById(id)
                .orElseThrow((() -> new ResourceNotFoundException("Profile with id:" + id + " not found")));
        return mapper.map(response, UserProfileWithAllDto.class);
    }

    @Override
    @Transactional
    public UserProfileWithAllDto create(UserProfileDto userProfileDto) {
        String name = AuthNameHolder.getAuthUsername();
        User user = userRepository.getByNameWithRoles(name);
        if (user.getProfile() == null) {
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setFirstname(userProfileDto.getFirstname());
            userProfile.setPhoneNumber(userProfile.getPhoneNumber());
            userProfile.setSurname(userProfile.getSurname());
            userProfile.setTown(userProfile.getTown());
            UserProfile response = userProfileRepository.save(userProfile);
            return mapper.map(response, UserProfileWithAllDto.class);
        }
        throw new AuthException("You already have a profile");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userProfileRepository.deleteById(id);
    }

    @Override
    public Page<UserProfileDto> findAll(Pageable pageable) {
        return userProfileRepository.findAll(pageable)
                .map(entity -> mapper.map(entity, UserProfileDto.class));
    }

    @Override
    @Transactional
    public Page<UserProfileDto> findFriends(Pageable pageable) {
        User user = userRepository.getByName(AuthNameHolder.getAuthUsername());
        UserProfile userProfile = userProfileRepository.findById(user.getProfile().getId()).orElseThrow((() -> new ResourceNotFoundException("Profile with id:" + user.getProfile().getId() + " not found")));
        List<UserProfileDto> friends =
                inviteRepository.findAllByStatus(InviteStatus.ACCEPTED, pageable).stream()
                        .filter(k -> k.getUserFrom().getId().equals(userProfile.getId()))
                        .map(Invite::getUserTo)
                        .map(entity -> mapper.map(entity, UserProfileDto.class))
                        .collect(Collectors.toList());
        friends.addAll(inviteRepository.findAllByStatus(InviteStatus.ACCEPTED, pageable).stream()
                .filter(k -> k.getUserTo().getId().equals(userProfile.getId()))
                .map(Invite::getUserFrom)
                .map(entity -> mapper.map(entity, UserProfileDto.class))
                .collect(Collectors.toList()));
        return new PageImpl<>(friends);
    }

}
