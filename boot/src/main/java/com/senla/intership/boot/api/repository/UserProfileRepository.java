package com.senla.intership.boot.api.repository;

import com.senla.intership.boot.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
