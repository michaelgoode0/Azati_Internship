package com.senla.intership.boot.api.repository;

import com.senla.intership.boot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
