package com.senla.intership.boot.api.repository;

import com.senla.intership.boot.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
    Hashtag findHashtagByValue(String value);
    @Query("select h.value from Hashtag h")
    List<String> findHashtagValues();
    @Query("select h from Hashtag h order by size(h.posts) desc")
    Page<Hashtag> getAllTop(Pageable pageable);
}
