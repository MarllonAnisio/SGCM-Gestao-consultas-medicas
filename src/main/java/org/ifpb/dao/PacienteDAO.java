package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import org.ifpb.dao.dao_exceptions.DatabaseException;
import org.ifpb.dao.interfaces.IPacienteDAO;
import org.ifpb.model.Paciente;

import java.util.List;
import java.util.Optional;

public class PacienteDAO extends GenericDAOImpl<Paciente, Long> implements IPacienteDAO {


    public PacienteDAO() {
        super(Paciente.class);
    }

    public Optional<Paciente> findByCpf(String cpf) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT p FROM Paciente p WHERE p.cpf = :cpf";
            return em.createQuery(jpql, Paciente.class)
                    .setParameter("cpf", cpf)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (Exception e) {
            throw new DatabaseException("Erro ao buscar paciente pelo CPF: " + cpf, e);
        }
    }

    public List<Paciente> findAllAtivos() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT p FROM Paciente p WHERE p.ativo = true";
            return em.createQuery(jpql, Paciente.class).getResultList();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao listar médicos ativos.", e);
        }
    }


    public List<Paciente> findAllInativos() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT p FROM Paciente p WHERE p.ativo = false";
            return em.createQuery(jpql, Paciente.class).getResultList();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao listar Pacientes inativos.", e);
        }
    }


    public Optional<Paciente> findByIdInclusiveInativos(Long id) {
        return super.findById(id);
    }


    public Optional<Paciente> findByIdAtivo(Long id) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT p FROM Paciente p WHERE p.ativo = true AND p.id = :id";

            return em.createQuery(jpql, Paciente.class)
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();

        } catch (Exception e) {
            throw new DatabaseException("Erro ao buscar Paciente ativo por ID.", e);
        }
    }
}
