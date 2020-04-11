package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoResumidoResponse {

    private String codigo;
    private String status;
    private RestauranteResumidoResponse restaurante;
    private OffsetDateTime dataCriacao;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private UsuarioResponse usuario;
}