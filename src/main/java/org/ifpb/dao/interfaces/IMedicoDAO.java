package org.ifpb.dao.interfaces;

import org.ifpb.model.Medico;

import java.util.List;
import java.util.Optional;

public interface IMedicoDAO extends GerericDAO<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
    List<Medico> findAllAtivos();
    List<Medico> findAllInativos();
    Optional<Medico> findByIdInclusiveInativos(Long id);
    Optional<Medico> findByIdAtivo(Long id);
}
