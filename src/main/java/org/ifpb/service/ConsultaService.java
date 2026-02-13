package org.ifpb.service;

import org.ifpb.dao.ConsultaDAO;
import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.PacienteDAO;
import org.ifpb.dto.consulta.ConsultaRequestDTO;
import org.ifpb.model.Consulta;
import org.ifpb.model.Medico;
import org.ifpb.model.Paciente;
import org.ifpb.service.service_exeptions.MedicoNaoEncontradoExeption;
import org.ifpb.service.service_exeptions.PacienteNaoEncontradoExeption;


public class ConsultaService {

    private ConsultaDAO consultaDAO;
    private MedicoDAO medicoDAO;
    private PacienteDAO pacienteDAO;

    public ConsultaService() {
        consultaDAO = new ConsultaDAO();
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
    }
    public void agendar(ConsultaRequestDTO consultaDTO) throws MedicoNaoEncontradoExeption, PacienteNaoEncontradoExeption {

        Medico medico = medicoDAO.findById(consultaDTO.getIdMedico())
                .orElseThrow(() -> new MedicoNaoEncontradoExeption("Medico não encontrado"));

        Paciente paciente = pacienteDAO.findById(consultaDTO.getIdPaciente())
                .orElseThrow(() -> new PacienteNaoEncontradoExeption("Paciente não encontrado"));

        Consulta consulta = Consulta.builder()
                .data(consultaDTO.getData())
                .observacao(consultaDTO.getObservacao())
                .medico(medico).
                paciente(paciente).
                build();

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        consultaDAO.save(consulta);
    }

}





