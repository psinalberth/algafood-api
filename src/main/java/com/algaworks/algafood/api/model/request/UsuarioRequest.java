package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioRequest {

    @ApiModelProperty(example = "Luiz Augusto Jota", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "lajota@domain.com", required = true, position = 5)
    @Email
    @NotBlank
    private String email;
}