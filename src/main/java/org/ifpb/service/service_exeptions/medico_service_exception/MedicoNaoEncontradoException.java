package org.ifpb.service.service_exeptions.medico_service_exception;

public class MedicoNaoEncontradoException extends RuntimeException {
    public MedicoNaoEncontradoException(String message) {
        super(message);
    }
}
