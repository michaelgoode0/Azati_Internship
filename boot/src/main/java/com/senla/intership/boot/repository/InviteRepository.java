package com.senla.intership.boot.repository;

import com.senla.intership.boot.entity.Invite;
import com.senla.intership.boot.entity.UserProfile;
import com.senla.intership.boot.enums.InviteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Invite findInviteByUserToAndUserFrom(UserProfile userTo, UserProfile userFrom);

    Page<Invite> findAllByStatus(InviteStatus status, Pageable pageable);

}

