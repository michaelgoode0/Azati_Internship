package com.azati.microservice.repository;

import com.azati.microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername (String username);
}
