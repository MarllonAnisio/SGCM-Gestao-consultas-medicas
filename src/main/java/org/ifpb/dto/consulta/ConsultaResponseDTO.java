package org.ifpb.dto.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.dto.medico.MedicoResponseDTO;
import org.ifpb.dto.paciente.PacienteResponseDTO;
import org.ifpb.model.enums.StatusConsulta;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter


public class ConsultaResponseDTO {

    private Long id;
    private LocalDateTime data;
    private StatusConsulta status;
    private String observacao;

    private MedicoResponseDTO medico;

    private PacienteResponseDTO paciente;

}

