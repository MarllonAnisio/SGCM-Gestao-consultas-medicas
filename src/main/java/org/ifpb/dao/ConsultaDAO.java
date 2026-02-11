package org.ifpb.dao;


import org.ifpb.dao.interfaces.IConsultaDAO;
import org.ifpb.model.Consulta;

public class ConsultaDAO extends GenericDAOImpl<Consulta, Long> implements IConsultaDAO {

    public ConsultaDAO() {
        super(Consulta.class);
    }

}
