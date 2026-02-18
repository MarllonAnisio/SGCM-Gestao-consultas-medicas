package org.ifpb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.ifpb.model.enums.Especialidade;
import org.ifpb.model.model_interfaces.IExclusaoLogica;

import java.util.List;
import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_medicos")
@Entity
@SQLDelete(sql = "UPDATE tb_medicos SET ativo = false WHERE id = ?")
public class Medico implements IExclusaoLogica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CRM é Obrigatório! ")
    @Pattern(
            regexp = "^\\d{6}-\\d{2}/[A-Z]{2}$",
            message = "CRM deve ter 6 digitos"
    )
    @Column(nullable = false,name =" crm", unique = true, length = 6)
    private String crm;

    @NotBlank(message = "Nome é Obrigatorio")
    @Size(message = "Nome deve ter entre  e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Builder.Default
    @Column(nullable = false)
    private boolean ativo = true;

    @NotNull(message = "Especialidade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Especialidade especialidade;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Consulta> consultas;

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", CRM='" + crm + '\'' +
                ", nome='" + nome + '\'' +
                ", especialidade=" + especialidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return Objects.equals(crm, medico.crm);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crm);
    }

    @Override
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public Boolean getAtivo() {
        return this.ativo;
    }
}
