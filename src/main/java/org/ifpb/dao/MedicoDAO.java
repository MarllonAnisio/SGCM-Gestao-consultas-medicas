package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery; // Importante para tipagem forte
import org.ifpb.dao.dao_exceptions.DatabaseException;
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

            return em.createQuery(jpql, Medico.class)
                    .setParameter("crm", crm)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao buscar médico pelo CRM: " + crm, e);
        }
    }

    @Override
    public boolean findByExistsCrm(String crm) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(m) FROM Medico m WHERE m.CRM = :crm";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("crm", crm)
                    .getSingleResult();

            return count > 0;

        } catch (Exception e) {
            throw new DatabaseException("Erro ao verificar existência do CRM.", e);
        }
    }

    @Override
    public List<Medico> findAllAtivos() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT m FROM Medico m WHERE m.ativo = true";
            return em.createQuery(jpql, Medico.class).getResultList();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao listar médicos ativos.", e);
        }
    }

    @Override
    public List<Medico> findAllInativos() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT m FROM Medico m WHERE m.ativo = false";
            return em.createQuery(jpql, Medico.class).getResultList();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao listar médicos inativos.", e);
        }
    }

    @Override
    public Optional<Medico> findByIdInclusiveInativos(Long id) {
        return super.findById(id);
    }

    @Override
    public Optional<Medico> findByIdAtivo(Long id) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT m FROM Medico m WHERE m.ativo = true AND m.id = :id";

            return em.createQuery(jpql, Medico.class)
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao buscar médico ativo por ID.", e);
        }
    }
}