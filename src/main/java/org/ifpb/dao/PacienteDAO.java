package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import org.ifpb.dao.interfaces.IPacienteDAO;
import org.ifpb.model.Paciente;

import java.util.List;
import java.util.Optional;

public class PacienteDAO extends GenericDAOImpl<Paciente, Long> implements IPacienteDAO {


    public PacienteDAO() {
        super(Paciente.class);
    }

    public Optional<Paciente> findByCpf(String cpf){
        try(EntityManager em = getEntityManager()){
            String jpql = "SELECT p FROM Paciente p WHERE p.cpf = :cpf";

            List<Paciente> resultado = em.createQuery(jpql, Paciente.class)
                    .setParameter("cpf", cpf)
                    .setMaxResults(1)
                    .getResultList();

            return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));

        }
    }
}
