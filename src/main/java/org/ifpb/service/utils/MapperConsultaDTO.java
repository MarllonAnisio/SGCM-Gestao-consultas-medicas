package org.ifpb.service.utils;

import org.ifpb.dto.consulta.ConsultaResponseDTO;
import org.ifpb.model.Consulta;

public class MapperConsultaDTO {

    public static ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        if (consulta == null) return null;

        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getData(),
                consulta.getStatus(),
                consulta.getObservacao(),

                MapperMedicoDTO.toResponseDTO(consulta.getMedico()),
                MapperPacienteDTO.toResponseDTO(consulta.getPaciente())
        );
    }
}
