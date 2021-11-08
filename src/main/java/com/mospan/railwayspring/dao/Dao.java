package com.mospan.railwayspring.dao;

import java.util.Collection;

public interface Dao<T> {
    void insert(T entity);
    void update(T entity);
    T find(String param);
    T findById(long id);
    void delete(T entity);
    Collection<T> findAll();
}
