package org.ifpb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.model.enums.StatusConsulta;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@Table(name = "consultas")
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
}
