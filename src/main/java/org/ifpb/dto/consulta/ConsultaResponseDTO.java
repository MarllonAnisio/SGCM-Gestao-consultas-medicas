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
/**
 * @class ConsultaResponseDTO
 * utilizing to transfer data from model to controller
 * @var id is the id of the consultation
 * @var data is the date of the consultation
 * @var status is the status of the consultation
 * @var observacao is the observation of the consultation
 * @var idPaciente is the id of the patient
 * @var nomePaciente is the name of the patient
 * @var cpfPaciente is the CPF(Brazilian Tax ID) of the patient
 * @var idMedico is the id of the doctor
 * @var NomeMedico is the name of the doctor
 * */
public class ConsultaResponseDTO {

    private Long id;
    private LocalDateTime data;
    private StatusConsulta status;
    private String observacao;


    private Long idPaciente;
    private String nomePaciente;
    private String cpfPaciente;

    private Long idMedico;
    private String NomeMedico;

}
