package org.ifpb.dao;

public interface GerericDAO<T, ID>{
    T save(T entity);
    T findById(ID id);
    void deleteById(ID id);
    T update(T entity);
    Iterable<T> findAll();
    boolean existsById(ID id);
    long count();
    void delete(T entity);
}
