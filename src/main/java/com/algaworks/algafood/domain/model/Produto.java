package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Produto extends EntidadeBase {

    private static final long serialVersionUID = -5390549029682443636L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private boolean ativo;

    @ManyToOne
    @JoinColumn
    private Restaurante restaurante;

    // Ativo/Inativo

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }
}