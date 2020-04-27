package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoResponse extends RepresentationModel<EstadoResponse> {

    @ApiModelProperty(example = "21")
    private Long id;

    @ApiModelProperty(example = "Maranhão")
    private String nome;
}