package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoResponse {

    @ApiModelProperty(example = "1", position = 1)
    private Long id;

    @ApiModelProperty(example = "Cartão de crédito", position = 2)
    private String descricao;
}