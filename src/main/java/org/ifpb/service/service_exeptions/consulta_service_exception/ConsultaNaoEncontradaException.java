package org.ifpb.service.service_exeptions.consulta_service_exception;

public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(String message) {
        super(message);
    }
}
