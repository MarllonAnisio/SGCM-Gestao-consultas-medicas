package org.ifpb.service.service_exeptions;

public class PacienteNaoEncontradoExeption extends RuntimeException {

    public PacienteNaoEncontradoExeption(String msg) {
        super(msg);
    }
    public PacienteNaoEncontradoExeption(String msg, Throwable cause) {
        super(msg, cause);
    }
}
