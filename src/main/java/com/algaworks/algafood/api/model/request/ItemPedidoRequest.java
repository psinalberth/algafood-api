package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemPedidoRequest {

    @NotNull(message = "{NotNull.item.produtoId}")
    private Long produtoId;

    @NotNull(message = "{NotNull.item.quantidade}")
    @Positive(message = "{Positive.item.quantidade}")
    private Integer quantidade;

    private String observacao;
}