package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import org.ifpb.config.HibernateUtil;
import org.ifpb.model.Consulta;

public class ConsultaDAO implements GerericDAO<Consulta, Long>{

    private EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public Consulta save(Consulta entity) {
        return null;
    }


    public Consulta findById(Long i) {
        return null;
    }


    public void deleteById(Long i) {

    }


    public Consulta update(Consulta entity) {
        return null;
    }


    public Iterable<Consulta> findAll() {
        return null;
    }


    public boolean existsById(Long i) {
        return false;
    }


    public long count() {
        return 0;
    }


    public void delete(Consulta entity) {

    }
}
