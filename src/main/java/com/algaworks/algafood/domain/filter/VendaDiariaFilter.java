package com.algaworks.algafood.domain.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class VendaDiariaFilter {

    private Long restauranteId;
    private OffsetDateTime dataCriacaoInicio;
    private OffsetDateTime dataCriacaoFim;
}