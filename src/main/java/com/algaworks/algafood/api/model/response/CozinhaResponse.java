package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaResponse {

    @ApiModelProperty(example = "1")
    private Integer id;

    @ApiModelProperty(example = "Tailandesa")
    private String nome;
}
