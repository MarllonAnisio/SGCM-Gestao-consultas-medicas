package org.ifpb.dao.interfaces;

import org.ifpb.model.Medico;

public interface IMedicoDAO extends GerericDAO<Medico, Long> {
    Medico findByCrm(String crm);
}
