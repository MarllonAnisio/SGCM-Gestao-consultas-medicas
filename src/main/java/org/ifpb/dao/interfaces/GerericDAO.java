package org.ifpb.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface GerericDAO<T, ID>{
    void save(T entity);
    T update(T entity);
    Optional<T> findById(ID id);
    void deleteById(ID id);
    List<T> findAll();
    boolean existsById(ID id);
    long count();
    void delete(T entity);
}
