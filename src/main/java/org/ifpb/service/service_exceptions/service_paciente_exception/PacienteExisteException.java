package org.ifpb.service.service_exceptions.service_paciente_exception;

public class PacienteExisteException extends RuntimeException {
    public PacienteExisteException(String message) {
        super(message);
    }
}
