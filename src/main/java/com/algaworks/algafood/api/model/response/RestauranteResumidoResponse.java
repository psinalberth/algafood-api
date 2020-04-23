package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteResumidoResponse {

    @JsonView(RestauranteView.ApenasNome.class)
    @ApiModelProperty(example = "1")
    private Long id;

    @JsonView(RestauranteView.ApenasNome.class)
    @ApiModelProperty(example = "Thai", position = 5)
    private String nome;

    @ApiModelProperty(position = 10)
    private CozinhaResponse cozinha;

    @ApiModelProperty(example = "7.50", position = 15)
    private BigDecimal taxaFrete;
}