package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AlteracaoSenhaRequest {

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "1234", required = true, position = 5)
    @NotBlank
    private String novaSenha;
}