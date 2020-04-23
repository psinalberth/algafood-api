package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Luiz Augusto Jota", position = 5)
    private String nome;

    @ApiModelProperty(example = "lajota@domain.com", position = 10)
    private String email;
}