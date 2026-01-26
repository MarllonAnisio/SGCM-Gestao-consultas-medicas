package org.ifpb.dto.medico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.model.enums.Especialidade;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @class MedicoRequestDTO
 * utilizing to transfer data from controller to model
 * */
public class MedicoRequestDTO {
    private String nome;
    private String CRM;
    private Especialidade especialidade;
}
