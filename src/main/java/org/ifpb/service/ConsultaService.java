package org.ifpb.service;

import org.ifpb.dao.ConsultaDAO;
import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.PacienteDAO;
import org.ifpb.dto.consulta.ConsultaRequestDTO;
import org.ifpb.model.Consulta;
import org.ifpb.model.Medico;
import org.ifpb.model.Paciente;
import org.ifpb.model.enums.StatusConsulta;
import org.ifpb.service.service_exeptions.consulta_service_exception.ConsultaJaCanceladaException;
import org.ifpb.service.service_exeptions.consulta_service_exception.ConsultaJaRealizadaException;
import org.ifpb.service.service_exeptions.consulta_service_exception.ConsultaNaoEncontradaException;
import org.ifpb.service.service_exeptions.medico_service_exception.MedicoNaoEncontradoException;
import org.ifpb.service.service_exeptions.service_paciente_exception.PacienteNaoEncontradoException;


public class ConsultaService {

    private ConsultaDAO consultaDAO;
    private MedicoDAO medicoDAO;
    private PacienteDAO pacienteDAO;

    public ConsultaService() {
        consultaDAO = new ConsultaDAO();
        medicoDAO = new MedicoDAO();
        pacienteDAO = new PacienteDAO();
    }
    public void agendarConsulta (ConsultaRequestDTO consultaDTO) throws MedicoNaoEncontradoException, PacienteNaoEncontradoException {

        Medico medico = medicoDAO.findById(consultaDTO.getIdMedico())
                .orElseThrow(() -> new MedicoNaoEncontradoException("Medico não encontrado"));

        Paciente paciente = pacienteDAO.findById(consultaDTO.getIdPaciente())
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));

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
    public void cancelarConsulta(Long idConsulta) {

        Consulta consulta = consultaDAO.findById(idConsulta)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("A consulta não foi encontrada"));

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new ConsultaJaCanceladaException("Consulta ja Está Cancelada ");
        } else if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new ConsultaJaRealizadaException("A consulta já foi realizada");
        }
        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaDAO.update(consulta);
    }

}





