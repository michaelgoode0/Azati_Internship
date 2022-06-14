package com.senla.intership.boot.util;

public interface GenericDao<T> {

    T save(final T entity);

    T update(final T entity);

    T deleteById(final Long id);

    T get(final Long id);
}
