package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe uma foto cadastrada para o produto com código %d e para o restaurante de código %d",
                produtoId, restauranteId));
    }
}