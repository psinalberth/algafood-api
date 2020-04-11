package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 4374466531367521182L;

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
