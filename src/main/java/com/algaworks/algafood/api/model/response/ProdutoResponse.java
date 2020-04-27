package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoResponse extends RepresentationModel<ProdutoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Khai Phad Met Ma Muang", position = 5)
    private String nome;

    @ApiModelProperty(example = "Sauté de Frango com Castanhas de Caju", position = 10)
    private String descricao;

    @ApiModelProperty(example = "58.00", position = 15)
    private BigDecimal preco;

    @ApiModelProperty(example = "true", position = 20)
    private boolean ativo;
}