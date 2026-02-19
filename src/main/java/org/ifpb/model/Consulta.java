package org.ifpb.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.model.enums.StatusConsulta;

import java.time.LocalDateTime;
import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "tb_consultas",
        indexes = {
                @Index(name = "idx_consulta_data", columnList = "data_consulta"),
                @Index(name = "idx_consulta_status", columnList = "status"),
                @Index(name = "idx_consulta_medico", columnList = "medico_id"),
                @Index(name = "idx_consulta_paciente", columnList = "paciente_id")
        }
)
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data da consulta é obrigatória")
    @Future(message = "Data da consulta deve estar no futuro")
    @Column(nullable = false)
    private LocalDateTime data;


    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    @Column(name = "observacoes", length = 500)
    private String observacao;


    @NotNull(message = "Status é Obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false, length = 50)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @NotNull(message = "Medico Obrigatorio")
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_medico"))
    private Medico medico;

    @NotNull(message = "Paciente Obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private Paciente paciente;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = StatusConsulta.AGENDADA;
        }
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", data=" + data +
                ", observacao='" + observacao + '\'' +
                ", status=" + status +
                ", medico=" + (medico != null ? medico.getNome() : "N/A") +  // ✅ Safe!
                ", paciente=" + (paciente != null ? paciente.getNome() : "N/A") +
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
