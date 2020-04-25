package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente", position = 5)
    private String nome;
}