package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponse {

    private String codigo;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private EnderecoResponse enderecoEntrega;
    private RestauranteResumidoResponse restaurante;
    private FormaPagamentoResponse formaPagamento;
    private UsuarioResponse cliente;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private List<ItemPedidoResponse> itens;
}