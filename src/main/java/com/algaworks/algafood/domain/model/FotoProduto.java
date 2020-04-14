package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class FotoProduto extends EntidadeBase {

    private static final long serialVersionUID = -8952932745444714927L;

    @Id
    @Column(name = "produto_id")
    @EqualsAndHashCode.Include
    private Long id;
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    public Long getRestauranteId() {

        if (this.getProduto() == null)
            return null;

        return this.getProduto().getRestaurante().getId();
    }
}