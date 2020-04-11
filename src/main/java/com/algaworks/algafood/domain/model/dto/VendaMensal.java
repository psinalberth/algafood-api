package com.algaworks.algafood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class VendaMensal {

    private Long totalVendas;
    private BigDecimal totalFaturado;
}