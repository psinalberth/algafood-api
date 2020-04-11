package com.algaworks.algafood.api.model.request;

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

    @Valid
    @NotNull
    private EnderecoRequest enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIdRequest formaPagamento;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoRequest> itens;
}