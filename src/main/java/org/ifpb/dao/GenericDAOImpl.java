package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import org.ifpb.config.HibernateUtil;

import java.util.List;

public abstract class GenericDAOImpl<T, ID> implements GerericDAO<T, ID> {

    private final Class<T> classe;

    public GenericDAOImpl(Class<T> classe) {
        this.classe = classe;
    }
    protected EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }


    @Override
    public T save(T entity) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return entity;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally{
            em.close();
        }
    }

    @Override
    public T findById(ID id) {
        return null;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(T entity) {

    }
}
