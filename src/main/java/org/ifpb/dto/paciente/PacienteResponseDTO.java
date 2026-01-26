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
 * */
public class PacienteResponseDTO {
    private int id;
    private String nome;
    private String cpf;
    int idade;
}
