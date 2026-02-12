package org.ifpb.dao;

import org.ifpb.dao.interfaces.IMedicoDAO;
import org.ifpb.model.Medico;

public class MedicoDAO extends GenericDAOImpl<Medico, Long> implements IMedicoDAO {

    public MedicoDAO() {
        super(Medico.class);
    }

    @Override
    public Medico findByCrm(String crm) {
        return null;
    }

}
