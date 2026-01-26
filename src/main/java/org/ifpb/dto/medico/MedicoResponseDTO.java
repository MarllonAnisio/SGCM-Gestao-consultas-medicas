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
 * */
public class MedicoResponseDTO {

    private int id;
    private String nome;
    private String CRM;
    private String especialidade;

}
