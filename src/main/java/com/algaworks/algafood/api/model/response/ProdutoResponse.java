package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean ativo;
}