package org.ifpb.dao.interfaces;

import org.ifpb.model.Paciente;
import java.util.Optional;

public interface IPacienteDAO extends GerericDAO<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);
}
