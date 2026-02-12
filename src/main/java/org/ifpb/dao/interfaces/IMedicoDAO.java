package org.ifpb.dao.interfaces;

import org.ifpb.model.Medico;

import java.util.Optional;

public interface IMedicoDAO extends GerericDAO<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
}
