package org.ifpb.service.service_exeptions;

public class MedicoNaoEncontradoException extends RuntimeException {
    public MedicoNaoEncontradoException(String message) {
        super(message);
    }
}
