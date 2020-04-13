package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteResumidoResponse {

    @JsonView(RestauranteView.ApenasNome.class)
    private Long id;

    @JsonView(RestauranteView.ApenasNome.class)
    private String nome;

    private CozinhaResponse cozinha;
    private BigDecimal taxaFrete;
}