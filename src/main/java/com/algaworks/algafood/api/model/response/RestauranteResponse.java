package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai", position = 5)
    private String nome;

    @ApiModelProperty(position = 10)
    private CozinhaResponse cozinha;

    @ApiModelProperty(example = "7.50", position = 15)
    private BigDecimal taxaFrete;

    @ApiModelProperty(position = 20)
    private EnderecoResponse endereco;

    @ApiModelProperty(example = "true", position = 25)
    private boolean ativo;

    @ApiModelProperty(example = "false", position = 30)
    private boolean aberto;
}