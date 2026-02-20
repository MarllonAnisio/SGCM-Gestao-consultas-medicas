package org.ifpb.service.utils;

import org.ifpb.dto.paciente.PacienteRequestDTO;
import org.ifpb.dto.paciente.PacienteResponseDTO; // Crie este DTO!
import org.ifpb.model.Paciente;

import java.time.LocalDate;

public class MapperPacienteDTO {

    public static Paciente toEntity(PacienteRequestDTO dto) {
        return Paciente.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .dataNascimento(dto.getDatanascimento())
                .build();
    }

    public static PacienteResponseDTO toResponseDTO(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getIdade(),
                paciente.getAtivo()
        );
    }
}