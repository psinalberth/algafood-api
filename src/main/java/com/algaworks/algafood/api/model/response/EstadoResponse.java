package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoResponse {

    @ApiModelProperty(example = "21")
    private Long id;

    @ApiModelProperty(example = "Maranhão")
    private String nome;
}