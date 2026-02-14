package org.ifpb.service.service_exeptions;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException(String msg) {
        super(msg);
    }

    public PacienteNaoEncontradoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
