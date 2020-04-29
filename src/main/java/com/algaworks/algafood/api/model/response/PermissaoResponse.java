package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
@Getter
@Setter
public class PermissaoResponse extends RepresentationModel<PermissaoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "INATIVAR_PRODUTO", position = 5)
    private String nome;

    @ApiModelProperty(example = "Permite a inativação de um produto.", position = 10)
    private String descricao;
}