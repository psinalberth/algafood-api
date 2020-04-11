package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoResponse {

    private Long produtoId;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}