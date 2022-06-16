package com.senla.intership.boot.repository.impl;

import com.senla.intership.boot.entity.User;
import com.senla.intership.boot.repository.UserRepository;
import com.senla.intership.boot.util.AbstractDao;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl extends AbstractDao<User> implements UserRepository {

    private final DataSource dataSource;

    @Override
    public Page<User> getByNameCriteria(String username, Pageable pageable) {
        return null;
    }

    @Override
    public User getByNameWithRoles(String username) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        final Root<User> from = query.from(User.class);
        from.fetch("roles", JoinType.LEFT);
        try {
            return entityManager.createQuery(
                    query.select(from).where(criteriaBuilder.like(from.get("username"), username))
            ).getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        }
    }

    @Override
    public User getByName(String username) {
        return entityManager.createQuery(
                "SELECT user FROM User user WHERE user.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery(
                "SELECT user FROM User user", User.class)
                .getResultList();
    }

    @Override
    public User getEagerJPQL(Long userId) {
        User user = entityManager.createQuery(
                "SELECT DISTINCT user FROM User user LEFT JOIN FETCH " +
                        "user.profile WHERE user.id = :id", User.class
        ).setParameter("id", userId).setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getSingleResult();

        user = entityManager.createQuery(
                "SELECT DISTINCT user FROM User user LEFT JOIN FETCH " +
                        "user.roles WHERE user IN :user", User.class
        ).setParameter("user", user).setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getSingleResult();

        return user;
    }

    @Override
    public User getEagerCriteria(Long userId) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        final Root<User> from = query.from(User.class);

        from.joinSet("roles", JoinType.LEFT);
        from.join("profile", JoinType.LEFT);

        query.where(criteriaBuilder.equal(from.get("id"), userId));

        return entityManager.createQuery(
                query)
                .getSingleResult();
    }

    @Override
    public User getEagerGraph(Long id) {
        EntityGraph<?> graph = this.entityManager.getEntityGraph("user_graph");
        Map hints = new HashMap();
        hints.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(User.class, id);
    }

    @Override
    @SneakyThrows
    public User getByLoginJdbc(String login) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement
                ("SELECT * FROM users WHERE username=?")) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        }
    }
}
