package org.ifpb.service;

import org.ifpb.dao.ConsultaDAO;
import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.PacienteDAO;


public class ConsultaService {

    private ConsultaDAO consultaDAO;
    private MedicoDAO medicoDAO;
    private PacienteDAO pacienteDAO;

    public ConsultaService() {
        consultaDAO = new ConsultaDAO();
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
    }


}





