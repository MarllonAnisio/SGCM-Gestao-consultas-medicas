package org.ifpb.service.utils;

import org.ifpb.dto.medico.MedicoRequestDTO;
import org.ifpb.dto.medico.MedicoResponseDTO; // Crie este DTO!
import org.ifpb.model.Medico;
import org.ifpb.model.enums.Especialidade;

public class MapperMedicoDTO {

    // Do Front para o Banco (Para Salvar/Atualizar)
    public static Medico toEntity(MedicoRequestDTO dto) {
        return Medico.builder()
                .crm(dto.getCrm())
                .nome(dto.getNome())
                .especialidade(Especialidade.valueOf(dto.getEspecialidade().toUpperCase()))
                .build();
    }

    // Do Banco para o Front (Para Listar/Buscar)
    public static MedicoResponseDTO toResponseDTO(Medico medico) {
        // Retorna só o que o front precisa ver!
        return new MedicoResponseDTO(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade().name(),
                medico.getAtivo()
        );
    }
}
