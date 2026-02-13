package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import org.ifpb.dao.interfaces.IMedicoDAO;
import org.ifpb.model.Medico;

import java.util.List;
import java.util.Optional;

public class MedicoDAO extends GenericDAOImpl<Medico, Long> implements IMedicoDAO {

    public MedicoDAO() {
        super(Medico.class);
    }

    @Override
    public Optional<Medico> findByCrm(String crm) {
        try (EntityManager em = getEntityManager()) {

            String jpql = "SELECT m FROM Medico m WHERE m.CRM = :crm";

            List<Medico> resultado = em.createQuery(jpql, Medico.class)
                    .setParameter("crm", crm)
                    .setMaxResults(1)
                    .getResultList();

            return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
        }
    }

    @Override
    public List<Medico> findAllAtivos() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT m FROM Medico m WHERE m.ativo = true";
            return em.createQuery(jpql, Medico.class).getResultList();
        }
    }
    @Override
    public List<Medico> findAllInativos() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT m FROM Medico m WHERE m.ativo = false";
            return em.createQuery(jpql, Medico.class).getResultList();
        }
    }
    
}
