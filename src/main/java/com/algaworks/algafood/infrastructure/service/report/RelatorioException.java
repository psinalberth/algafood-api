package com.algaworks.algafood.infrastructure.service.report;

public class RelatorioException extends RuntimeException {

    private static final long serialVersionUID = -2250205751470741531L;

    public RelatorioException(String message) {
        super(message);
    }

    public RelatorioException(String message, Throwable cause) {
        super(message, cause);
    }
}