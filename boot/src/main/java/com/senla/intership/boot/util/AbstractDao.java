package com.senla.intership.boot.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDao<T> implements GenericDao<T> {
    private final Class<T> clazz;
    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDao() {
        this.clazz = getGenericEntityClass();
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public T get(Long id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public T deleteById(Long entityId) {
        final T entity = get(entityId);
        if (entity != null) {
            entityManager.remove(entity);
        }
        return entity;
    }

    private Class<T> getGenericEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}
