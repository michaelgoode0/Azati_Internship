package com.senla.intership.boot.repository;

import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.util.GenericDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository extends GenericDao<User> {
    Page<User> getByNameCriteria(String username, Pageable pageable);

    User getByNameWithRoles(String username);

    User getByName(String username);

    User getEagerJPQL(Long userId);

    User getEagerCriteria(Long userId);

    User getEagerGraph(Long id);

    User getByLoginJdbc(String login);

    List<User> getAll();
}
