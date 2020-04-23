package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoResponse {

    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Khai Phad Met Ma Muang", position = 5)
    private String nomeProduto;

    @ApiModelProperty(example = "2", position = 10)
    private Integer quantidade;

    @ApiModelProperty(example = "58.00", position = 15)
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "116.00", position = 20)
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Pimenta extra", position = 25)
    private String observacao;
}