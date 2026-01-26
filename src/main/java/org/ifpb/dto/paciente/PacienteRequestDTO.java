package org.ifpb.dto.paciente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @Class PacienteRequestDTO
 * utilizing to transfer data from controller to model
 * */
public class PacienteRequestDTO {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private int datanascimento;
}
