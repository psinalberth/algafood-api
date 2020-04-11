package com.algaworks.algafood.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {

    private Long clienteId;
    private Long restauranteId;
}