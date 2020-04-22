package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResponse {

    @ApiModelProperty(example = "2111300")
    private Long id;

    @ApiModelProperty(example = "São Luís")
    private String nome;
    private EstadoResponse estado;
}