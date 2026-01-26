package org.ifpb.dto.medico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @class MedicoResponseDTO
 * utilizing to transfer data from model to controller
 * @var id is the id of the doctor
 * @var nome is the name of the doctor
 * @var CRM is the Clinical Registration of the doctor
 * @var especialidade is the specialty of the doctor
 * */
public class MedicoResponseDTO {

    private int id;
    private String nome;
    private String CRM;
    private String especialidade;

}
