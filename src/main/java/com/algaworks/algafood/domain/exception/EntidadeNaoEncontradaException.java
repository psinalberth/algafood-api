package com.algaworks.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
}