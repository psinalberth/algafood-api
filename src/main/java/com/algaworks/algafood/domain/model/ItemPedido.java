package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemPedido extends EntidadeBase {

    private static final long serialVersionUID = -8995567729714468501L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn
    private Produto produto;

    @ManyToOne
    @JoinColumn
    private Pedido pedido;

    @NotNull
    private Integer quantidade;

    @NotNull
    private BigDecimal precoUnitario;

    @NotNull
    private BigDecimal precoTotal;

    private String observacao;

    public void calcularPrecoTotal() {
        BigDecimal precoUnitario = this.getPrecoUnitario() != null ? this.getPrecoUnitario() : BigDecimal.ZERO;
        Integer quantidade = this.getQuantidade() != null ? this.getQuantidade() : 0;
        this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
    }
}