package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSenhaRequest extends UsuarioRequest {

    @ApiModelProperty(example = "123", required = true, position = 10)
    private String senha;
}