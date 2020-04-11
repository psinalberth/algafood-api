package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
}