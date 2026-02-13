package org.ifpb.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.ifpb.model.model_interfaces.IExclusaoLogica;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_pacientes")
@SQLDelete(sql = "UPDATE tb_pacientes SET ativo = false WHERE id = ?")
public class Paciente implements IExclusaoLogica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(length=11, nullable=false, unique = true)
    private String cpf;

    private String telefone;

    @Builder.Default
    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private int datanascimento;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;


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

    @Override
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public Boolean getAtivo() {
        return ativo;
    }
}
