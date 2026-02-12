package org.ifpb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.dto.consulta.ConsultaRequestDTO;
import org.ifpb.model.enums.StatusConsulta;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_consultas")
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(name = "observacoes")
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Paciente paciente;

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", data=" + data +
                ", observacao='" + observacao + '\'' +
                ", status=" + status +
                ", medico=" + medico.getNome() +
                ", paciente=" + paciente.getNome() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
