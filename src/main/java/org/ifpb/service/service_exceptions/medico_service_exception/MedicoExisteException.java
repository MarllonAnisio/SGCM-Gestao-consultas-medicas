package org.ifpb.service.service_exceptions.medico_service_exception;

public class MedicoExisteException extends RuntimeException {

    protected static final String msg = "Medico Já existe no sistema com Este CRM";

    public MedicoExisteException() {
        super(msg);
    }
}
