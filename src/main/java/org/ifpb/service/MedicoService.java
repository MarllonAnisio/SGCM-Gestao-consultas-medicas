package org.ifpb.service;

import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.dao_exceptions.DatabaseException;
import org.ifpb.dao.interfaces.IMedicoDAO;
import org.ifpb.dto.medico.MedicoRequestDTO;
import org.ifpb.dto.medico.MedicoResponseDTO;
import org.ifpb.model.Medico;
import org.ifpb.model.enums.Especialidade;
import org.ifpb.service.service_exceptions.medico_service_exception.*;
import org.ifpb.service.service_interfaces.IAtivosService;
import org.ifpb.service.utils.MapperMedicoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MedicoService implements IAtivosService {

    private final IMedicoDAO medicoDAO;

    public MedicoService() {
        this.medicoDAO = new MedicoDAO();
    }

    // ================= LEITURAS (READ) =================

    public List<MedicoResponseDTO> listarAtivos() {
        return medicoDAO.findAllAtivos()
                .stream()
                .map(MapperMedicoDTO::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoDAO.findByIdAtivo(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException("Médico não encontrado ou inativo no sistema."));
        return MapperMedicoDTO.toResponseDTO(medico);
    }

    // ================= ESCRITAS (CREATE / UPDATE) =================

    public MedicoResponseDTO save(MedicoRequestDTO dto) {
        if (medicoDAO.findByExistsCrm(dto.getCrm())) {
            throw new MedicoExisteException("Já existe um médico cadastrado com o CRM: " + dto.getCrm());
        }

        try {
            Medico medico = MapperMedicoDTO.toEntity(dto);
            medicoDAO.save(medico);
            return MapperMedicoDTO.toResponseDTO(medico);

        } catch (IllegalArgumentException e) {
            throw new SaveInconsistencyException("Especialidade inválida: " + dto.getEspecialidade());
        } catch (DatabaseException e) {
            throw new SaveInconsistencyException("Erro de banco de dados ao salvar médico: " + e.getMessage());
        }
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
        Medico medico = medicoDAO.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException("Médico não encontrado para atualização."));


        medico.setNome(dto.getNome());

        try {
            medico.setEspecialidade(Especialidade.valueOf(dto.getEspecialidade().toUpperCase()));
            Medico medicoAtualizado = medicoDAO.update(medico);
            return MapperMedicoDTO.toResponseDTO(medicoAtualizado);

        } catch (IllegalArgumentException e) {
            throw new SaveInconsistencyException("Especialidade inválida para atualização: " + dto.getEspecialidade());
        }
    }

    // ================= ATIVAÇÃO / INATIVAÇÃO (DELETE LÓGICO) =================

    @Override
    public void ativar(Long id) {
        Medico medico = medicoDAO.findByIdInclusiveInativos(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException("Este médico não existe no sistema."));

        if (medico.getAtivo()) {
            throw new MedicoAtivoException("Este médico já está ativo.");
        }

        medico.setAtivo(true);
        medicoDAO.update(medico);
    }

    @Override
    public void inativar(Long id) {
        Medico medico = medicoDAO.findByIdAtivo(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException("Médico não encontrado ou já está inativo."));

        medico.setAtivo(false);
        medicoDAO.update(medico);
    }
}