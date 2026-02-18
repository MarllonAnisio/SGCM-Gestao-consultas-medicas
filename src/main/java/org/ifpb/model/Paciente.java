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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.br.CPF;
import org.ifpb.model.model_interfaces.IExclusaoLogica;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "tb_pacientes")
@SQLDelete(sql = "UPDATE tb_pacientes SET ativo = false WHERE id = ?")
public class Paciente implements IExclusaoLogica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é Obrigatório")
    @Size(message = "Nome deve ter entre  e 100 caracteres", min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @CPF
    @NotBlank(message = "CPF é Obrigatório")
    @Column(length=11, nullable=false, unique = true)
    private String cpf;

    @Pattern(
            regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
            message = "Telefone inválido. Formato: (11) 91234-5678"
    )
    private String telefone;

    @Builder.Default
    @Column(nullable = false)
    private boolean ativo = true;

    @Email(message = "Email inválido")
    @NotBlank(message = "O Paciente deve ter email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Data de Nascimento é Obrigatória")
    @Past(message = "Data de Nascimento deve ser anterior a data atual")
    @Column(nullable = false, name = "data_nascimento")
    private LocalDate dataNascimento;

    @Builder.Default
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
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
                ", datanascimento=" + dataNascimento +
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
