package org.ifpb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifpb.dto.medico.MedicoRequestDTO;
import org.ifpb.model.enums.Especialidade;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tb_medicos")
@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String CRM;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    public Medico(){

    }

    public Medico(Long id, String CRM, String nome, Especialidade especialidade) {
        this.id = id;
        this.CRM = CRM;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Medico(MedicoRequestDTO medicoRequestDTO) {
        this.CRM = medicoRequestDTO.getCRM();
        this.nome = medicoRequestDTO.getNome();
        this.especialidade = medicoRequestDTO.getEspecialidade();
    }

}
