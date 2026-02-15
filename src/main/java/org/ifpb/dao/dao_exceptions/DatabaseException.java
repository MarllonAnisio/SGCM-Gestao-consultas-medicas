package org.ifpb.dao.dao_exceptions;

public class DatabaseException extends RuntimeException {

    // Construtor para embrulhar o erro original (cause)
    public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    // Construtor para mensagens simples
    public DatabaseException(String msg) {
        super(msg);
    }
}
