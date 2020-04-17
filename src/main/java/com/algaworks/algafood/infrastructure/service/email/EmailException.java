package com.algaworks.algafood.infrastructure.service.email;

public class EmailException extends RuntimeException {

    private static final long serialVersionUID = -819198940855758728L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}