package org.ifpb.dao;

import org.ifpb.model.Paciente;

public class PacienteDAO extends GenericDAOImpl<Paciente, Long> implements IPacienteDAO {


    public PacienteDAO() {
        super(Paciente.class);
    }

    public Paciente findByCpf(String cpf){
        try{
            String jpql = "SELECT p FROM Paciente p WHERE p.cpf = :cpf";
            return getEntityManager()
                    .createQuery(jpql, Paciente.class).
                    setParameter("cpf", cpf).
                    getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
