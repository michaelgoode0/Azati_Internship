package com.senla.intership.boot.api.repository;

import com.senla.intership.boot.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment,Long> {
}
