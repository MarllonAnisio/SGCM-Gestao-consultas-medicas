package org.ifpb.dao.interfaces;

import org.ifpb.model.Paciente;
import java.util.Optional;

public interface IPacienteDAO extends GenericDAO<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);
}
