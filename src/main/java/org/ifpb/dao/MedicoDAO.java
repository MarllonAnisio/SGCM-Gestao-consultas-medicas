package org.ifpb.dao;

import org.ifpb.dao.interfaces.IMedicoDAO;
import org.ifpb.model.Medico;

public class MedicoDAO extends GenericDAOImpl<Medico, Long> implements IMedicoDAO {

    public MedicoDAO(Class<Medico> classe) {
        super(classe);
    }

    @Override
    public Medico findByCrm(String crm) {
        return null;
    }

}
