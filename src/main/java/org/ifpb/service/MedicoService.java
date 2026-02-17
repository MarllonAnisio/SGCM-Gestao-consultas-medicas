package org.ifpb.service;

import org.ifpb.dao.MedicoDAO;
import org.ifpb.dao.dao_exceptions.DatabaseException;
import org.ifpb.dao.interfaces.IMedicoDAO;
import org.ifpb.dto.medico.MedicoRequestDTO;
import org.ifpb.model.Medico;
import org.ifpb.model.enums.Especialidade;
import org.ifpb.service.service_exceptions.medico_service_exception.MedicoAtivoException;
import org.ifpb.service.service_exceptions.medico_service_exception.MedicoExisteException;

import org.ifpb.service.service_exceptions.medico_service_exception.MedicoNaoEncontradoException;
import org.ifpb.service.service_exceptions.medico_service_exception.SaveInconsistencyException;
import org.ifpb.service.service_interfaces.IAtivosService;

public class MedicoService implements IAtivosService {

    private final IMedicoDAO medicoDAO;

    public MedicoService(){
        medicoDAO = new MedicoDAO();
    }

    public void ativar(Long id) {
        Medico medico = medicoDAO.findByIdInclusiveInativos(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(" Este medico não existe no sistema"));

        if (medico.getAtivo()) {
            throw new MedicoAtivoException("Este medico já está ativo");
        }
        medico.setAtivo(true);

        medicoDAO.save(medico);

    }

    @Override
    public void inativar(Long id) {
        Medico medico = medicoDAO.findByIdAtivo(id)
                .orElseThrow(() -> new MedicoAtivoException("medico não encontrado como ativo no sistema"));

        medico.setAtivo(false);

        medicoDAO.update(medico);
    }
    public void save(MedicoRequestDTO medicoDTO){
        Medico medico = Medico.builder()
                .crm(medicoDTO.getCRM())
                .nome(medicoDTO.getNome())
                .especialidade(Especialidade.valueOf(medicoDTO.getEspecialidade()))
                .build();
        try {
            if (medicoDAO.findByExistsCrm(medico.getCrm())) {
                throw new MedicoExisteException();
            }
            medicoDAO.save(medico);

        }catch (DatabaseException e){
            throw new SaveInconsistencyException("Erro ao salvar medico: " + e.getMessage());
        }
    }
}
