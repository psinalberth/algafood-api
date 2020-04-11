package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemPedidoRequest {

    @NotNull(message = "{item.produtoId.not.null}")
    private Long produtoId;

    @NotNull(message = "{item.quantidade.not.null}")
    @Positive
    private Integer quantidade;

    private String observacao;
}