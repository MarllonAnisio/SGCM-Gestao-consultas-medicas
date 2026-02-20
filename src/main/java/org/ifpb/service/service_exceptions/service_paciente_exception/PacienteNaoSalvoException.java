package org.ifpb.service.service_exceptions.service_paciente_exception;

public class PacienteNaoSalvoException extends RuntimeException {
    public PacienteNaoSalvoException(String message) {
        super(message);
    }
}
