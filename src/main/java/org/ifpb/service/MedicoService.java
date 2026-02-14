package org.ifpb.service;

import org.ifpb.dao.MedicoDAO;
import org.ifpb.model.Medico;
import org.ifpb.service.service_exeptions.MedicoAtivoException;
import org.ifpb.service.service_exeptions.MedicoJaAtivoException;
import org.ifpb.service.service_exeptions.MedicoNaoEncontradoException;
import org.ifpb.service.service_interfaces.IAtivosService;

public class MedicoService implements IAtivosService {

    private MedicoDAO medicoDAO;

    public MedicoService(){
        medicoDAO = new MedicoDAO();
    }

    public void ativar(Long id) {
        Medico medico = medicoDAO.findByIdInclusiveInativos(id)
                .orElseThrow(() -> new MedicoNaoEncontradoException(" Este medico não existe no sistema"));

        if (medico.getAtivo()) {
            throw new MedicoJaAtivoException("Este medico já está ativo");
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
}
