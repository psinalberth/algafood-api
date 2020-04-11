package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AlteracaoSenhaRequest {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;
}