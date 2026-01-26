package org.ifpb.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Especialidades {
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    GINECOLOGIA("Ginecologia"),
    PEDIATRIA("Pediatria"),
    ORTOPEDIA("Ortopedia"),
    UROLOGIA("Urologia"),
    NEUROLOGIA("Neurologia"),
    GERIATRIA("Geriatria"),
    PSIQUIATRIA("Psiquiatria"),
    FISIOTERAPIA("Fisioterapia"),
    OBSTETRIA("Obstetricia"),
    CLINICA_GERAL("Clinica Geral");

    private final String descricao;
}
