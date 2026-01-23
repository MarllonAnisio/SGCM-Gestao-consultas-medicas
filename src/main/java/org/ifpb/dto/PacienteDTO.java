package org.ifpb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class PacienteDTO {

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private int datanascimento;


}
