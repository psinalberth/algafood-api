package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissaoRequest {

    @ApiModelProperty(example = "INATIVAR_PRODUTO", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Permite a inativação de um produto.", required = true, position = 5)
    @NotBlank
    private String descricao;
}