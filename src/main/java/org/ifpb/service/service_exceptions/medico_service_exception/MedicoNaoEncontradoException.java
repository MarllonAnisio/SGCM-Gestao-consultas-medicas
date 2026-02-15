package org.ifpb.service.service_exceptions.medico_service_exception;

public class MedicoNaoEncontradoException extends RuntimeException {
    public MedicoNaoEncontradoException(String message) {
        super(message);
    }
}
