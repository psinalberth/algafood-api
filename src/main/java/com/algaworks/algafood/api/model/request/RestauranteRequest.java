package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteRequest {

    @ApiModelProperty(example = "Thai", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "7.50", required = true, position = 5)
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @ApiModelProperty(position = 10)
    @Valid
    @NotNull
    private CozinhaIdRequest cozinha;

    @ApiModelProperty(position = 15)
    @Valid
    @NotNull
    private EnderecoRequest endereco;
}