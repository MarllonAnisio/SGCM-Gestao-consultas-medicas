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
 * @var nome is the name of the patient
 * @var cpf is the CPF(Brazilian Tax ID) of the patient
 * @var telefone is the phone number of the patient
 * @var email is the email of the patient
 * @var datanascimento is the birth date of the patient
 * */
public class PacienteRequestDTO {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private int datanascimento;
}
