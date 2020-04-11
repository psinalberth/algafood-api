package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class FormaPagamento extends EntidadeBase {

    private static final long serialVersionUID = -6334498517156804884L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String descricao;
}