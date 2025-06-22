package org.example.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
    void delete(ID id);
}
