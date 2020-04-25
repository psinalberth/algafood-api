package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "INATIVAR_PRODUTO", position = 5)
    private String nome;

    @ApiModelProperty(example = "Permite a inativação de um produto.", position = 10)
    private String descricao;
}