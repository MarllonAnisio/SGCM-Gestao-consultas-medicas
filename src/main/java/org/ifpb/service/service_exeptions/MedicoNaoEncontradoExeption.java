package org.ifpb.service.service_exeptions;

public class MedicoNaoEncontradoExeption extends RuntimeException {

    public MedicoNaoEncontradoExeption(String message) {
        super(message);
    }
}
