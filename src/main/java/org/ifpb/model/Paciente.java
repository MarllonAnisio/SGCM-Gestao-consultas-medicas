package org.ifpb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.dto.PacienteDTO;

import java.util.Objects;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @Column(length=11, nullable=false, unique = true)
    private String cpf;

    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private int datanascimento;

    public Paciente(PacienteDTO pacienteDTO) {
        this.nome = pacienteDTO.getNome();
        this.cpf = pacienteDTO.getCpf();
        this.telefone = pacienteDTO.getTelefone();
        this.email = pacienteDTO.getEmail();
        this.datanascimento = pacienteDTO.getDatanascimento();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cpf, paciente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }


    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", datanascimento=" + datanascimento +
                '}';
    }
}
