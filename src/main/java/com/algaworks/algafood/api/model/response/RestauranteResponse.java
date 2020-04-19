package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteResponse {

    private Long id;
    private String nome;
    private CozinhaResponse cozinha;
    private BigDecimal taxaFrete;
    private EnderecoResponse endereco;
    private boolean ativo;
    private boolean aberto;
}