package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d",
                produtoId, restauranteId));
    }
}