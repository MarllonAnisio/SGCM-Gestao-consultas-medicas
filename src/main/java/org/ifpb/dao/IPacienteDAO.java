package org.ifpb.dao;

import org.ifpb.model.Paciente;

public interface IPacienteDAO extends GerericDAO<Paciente, Long>{
    Paciente findByCpf(String cpf);

}
