package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResponse {

    private Long id;
    private String nome;
    private EstadoResponse estado;
}