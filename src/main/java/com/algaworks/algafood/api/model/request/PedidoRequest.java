package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {

    @Valid
    @NotNull
    private RestauranteIdRequest restaurante;

    @ApiModelProperty(position = 5)
    @Valid
    @NotNull
    private EnderecoRequest enderecoEntrega;

    @ApiModelProperty(position = 10)
    @Valid
    @NotNull
    private FormaPagamentoIdRequest formaPagamento;

    @ApiModelProperty(position = 15)
    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoRequest> itens;
}