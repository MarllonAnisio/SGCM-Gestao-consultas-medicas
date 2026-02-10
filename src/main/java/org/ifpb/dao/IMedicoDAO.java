package org.ifpb.dao;

import org.ifpb.model.Medico;

public interface IMedicoDAO extends GerericDAO<Medico, Long>{
    Medico findByCrm(String crm);
}
