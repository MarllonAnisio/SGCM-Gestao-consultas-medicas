package org.ifpb.service.service_exceptions.consulta_service_exception;

public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(String message) {
        super(message);
    }
}
