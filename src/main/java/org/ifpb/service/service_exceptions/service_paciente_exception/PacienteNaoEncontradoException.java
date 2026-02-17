package org.ifpb.service.service_exceptions.service_paciente_exception;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException(String msg) {
        super(msg);
    }

    public PacienteNaoEncontradoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
