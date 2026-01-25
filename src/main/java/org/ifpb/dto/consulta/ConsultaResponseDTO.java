package org.ifpb.dto.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.model.enums.StatusConsulta;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ConsultaResponseDTO {

    private int id;
    private LocalDateTime data;
    private StatusConsulta status;
    private String observacao;


    private int idPaciente;
    private String nomePaciente;
    private String cpfPaciente;

    private int idMedico;
    private String NomeMedico;

}
