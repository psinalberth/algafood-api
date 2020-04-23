package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemPedidoRequest {

    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "{NotNull.item.produtoId}")
    private Long produtoId;

    @ApiModelProperty(example = "2", required = true, position = 5)
    @NotNull(message = "{NotNull.item.quantidade}")
    @Positive(message = "{Positive.item.quantidade}")
    private Integer quantidade;

    @ApiModelProperty(example = "Pimenta extra", position = 10)
    private String observacao;
}