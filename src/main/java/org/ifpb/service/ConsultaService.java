package org.ifpb.service;

import org.ifpb.dao.ConsultaDAO;
import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.PacienteDAO;
import org.ifpb.dto.consulta.ConsultaRequestDTO;
import org.ifpb.dto.consulta.ConsultaResponseDTO;
import org.ifpb.model.Consulta;
import org.ifpb.model.Medico;
import org.ifpb.model.Paciente;
import org.ifpb.model.enums.StatusConsulta;
import org.ifpb.service.service_exceptions.consulta_service_exception.*;
import org.ifpb.service.service_exceptions.medico_service_exception.MedicoNaoEncontradoException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteInativoException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteNaoEncontradoException;
import org.ifpb.service.utils.MapperConsultaDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ConsultaService {

    private final ConsultaDAO consultaDAO;
    private final MedicoDAO medicoDAO;
    private final PacienteDAO pacienteDAO;

    public ConsultaService() {
        this.consultaDAO = new ConsultaDAO();
        this.medicoDAO = new MedicoDAO();
        this.pacienteDAO = new PacienteDAO();
    }

    // ================= LEITURAS (READ) =================

    public List<ConsultaResponseDTO> listarTodas() {
        return consultaDAO.findAll().stream()
                .map(MapperConsultaDTO::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResponseDTO buscarPorId(Long id) {
        Consulta consulta = consultaDAO.findById(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada no sistema."));
        return MapperConsultaDTO.toResponseDTO(consulta);
    }

    // ================= ESCRITAS (CREATE / UPDATE) =================

    public ConsultaResponseDTO agendarConsulta(ConsultaRequestDTO dto) {


        Medico medico = medicoDAO.findByIdAtivo(dto.getIdMedico())
                .orElseThrow(() -> new MedicoNaoEncontradoException("Médico não encontrado ou está inativo. Não é possível agendar."));

        Paciente paciente = pacienteDAO.findById(dto.getIdPaciente())
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado."));

        if (!paciente.isAtivo()) {
            throw new PacienteInativoException("Paciente inativo. Regularize o cadastro antes de agendar.");
        }


        // 3. Monta a Consulta
        Consulta consulta = Consulta.builder()
                .data(dto.getData())
                .observacao(dto.getObservacao())
                .medico(medico)
                .paciente(paciente)
                .build();

        consultaDAO.save(consulta);

        return MapperConsultaDTO.toResponseDTO(consulta);
    }

    public ConsultaResponseDTO cancelarConsulta(Long idConsulta) {
        Consulta consulta = consultaDAO.findById(idConsulta)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("A consulta não foi encontrada."));


        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new ConsultaJaCanceladaException("Esta consulta já se encontra cancelada.");
        }
        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new ConsultaJaRealizadaException("A consulta já foi realizada e o histórico não pode ser apagado.");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        Consulta atualizada = consultaDAO.update(consulta);

        return MapperConsultaDTO.toResponseDTO(atualizada);
    }
}