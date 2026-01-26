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
 *
 * @Class PacienteResponseDTO
 *
 * utilizing to transfer data from model to controller
 * @var id is the id of the patient
 * @var nome is the name of the patient
 * @var cpf is the CPF(Brazilian Tax ID) of the patient
 * @var idade is the age of the patient
 * */
public class PacienteResponseDTO {
    private int id;
    private String nome;
    private String cpf;
    int idade;
}
