package org.ifpb.service;

import org.ifpb.dao.PacienteDAO;
import org.ifpb.dao.dao_exceptions.DatabaseException;
import org.ifpb.dto.paciente.PacienteRequestDTO;
import org.ifpb.dto.paciente.PacienteResponseDTO;
import org.ifpb.model.Paciente;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteExisteException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteInativoException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteJaAtivoException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteNaoEncontradoException;
import org.ifpb.service.service_exceptions.service_paciente_exception.PacienteNaoSalvoException;
import org.ifpb.service.service_interfaces.IAtivosService;
import org.ifpb.service.utils.MapperPacienteDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PacienteService implements IAtivosService {

    private final PacienteDAO pacienteDAO;

    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }

    // ================= LEITURAS (READ) =================

    public List<PacienteResponseDTO> listarAtivos() {
        return pacienteDAO.findAllAtivos()
                .stream()
                .map(MapperPacienteDTO::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteDAO.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado no sistema."));

        if (!paciente.isAtivo()) {
            throw new PacienteNaoEncontradoException("Paciente inativo.");
        }
        return MapperPacienteDTO.toResponseDTO(paciente);
    }

    // ================= ESCRITAS (CREATE / UPDATE) =================

    public PacienteResponseDTO save(PacienteRequestDTO dto) {
        if (pacienteDAO.findByCpf(dto.getCpf()).isPresent()) {
            throw new PacienteExisteException("Já existe um paciente cadastrado com este CPF.");
        }

        try {
            Paciente paciente = MapperPacienteDTO.toEntity(dto);
            pacienteDAO.save(paciente);
            return MapperPacienteDTO.toResponseDTO(paciente);
        } catch (DatabaseException e) {
            throw new PacienteNaoSalvoException("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {
        Paciente paciente = pacienteDAO.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado para atualização."));

        // Regra de Negócio: não atualizamos o CPF por ser unico, apenas o restante
        paciente.setNome(dto.getNome());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEmail(dto.getEmail());

        Paciente atualizado = pacienteDAO.update(paciente);
        return MapperPacienteDTO.toResponseDTO(atualizado);
    }

    // ================= ATIVAÇÃO / INATIVAÇÃO (DELETE LÓGICO) =================

    @Override
    public void ativar(Long id) {
        Paciente paciente = pacienteDAO.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado."));

        if (paciente.isAtivo()) {
            throw new PacienteJaAtivoException("Este paciente já está ativo.");
        }

        paciente.ativar();
        pacienteDAO.update(paciente);
    }

    @Override
    public void inativar(Long id) {
        Paciente paciente = pacienteDAO.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado."));

        if (!paciente.isAtivo()) {
            throw new PacienteInativoException("Paciente já está inativo.");
        }

        paciente.inativar();
        pacienteDAO.update(paciente);
    }
}