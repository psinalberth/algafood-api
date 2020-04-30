package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.EstadoIdRequest;
import com.algaworks.algafood.api.model.request.EstadoRequest;
import com.algaworks.algafood.api.model.response.EstadoResponse;
import com.algaworks.algafood.domain.model.Estado;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToEstado;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstados;

@Mapper
public interface EstadoMapper extends RepresentationModelAssembler<Estado, EstadoResponse> {

    Estado toDomain(EstadoRequest request);

    Estado toDomain(EstadoIdRequest request);

    Estado toDomainCopy(@MappingTarget Estado estado, EstadoRequest request);

    EstadoResponse toModel(Estado estado);

    @AfterMapping
    default void addLinks(@MappingTarget EstadoResponse estadoResponse) {
        estadoResponse.add(linkToEstado(estadoResponse.getId()));
        estadoResponse.add(linkToEstados("estados"));
    }

    @Override
    default CollectionModel<EstadoResponse> toCollectionModel(Iterable<? extends Estado> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkToEstados());
    }
}