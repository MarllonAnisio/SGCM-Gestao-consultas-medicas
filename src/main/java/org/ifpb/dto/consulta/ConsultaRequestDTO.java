package org.ifpb.dto.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @Class ConsultaRequestDTO
 * utilizing to transfer data from controller to model
 * @var idPaciente  is the id of the patient
 * @var idMedico is the id of the doctor
 * @var data is the date of the consultation
 * */
public class ConsultaRequestDTO {

    private int idPaciente;
    private int idMedico;
    private LocalDateTime data;

}
