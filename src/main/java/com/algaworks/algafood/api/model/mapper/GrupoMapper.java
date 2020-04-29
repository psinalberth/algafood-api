package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.domain.model.Grupo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface GrupoMapper extends RepresentationModelAssembler<Grupo, GrupoResponse> {

    Grupo toDomain(GrupoRequest request);

    Grupo toDomainCopy(@MappingTarget Grupo grupo, GrupoRequest request);

    @Mapping(target = "id", source = "grupoId")
    Grupo map(Long grupoId);

    GrupoResponse toModel(Grupo grupo);

    @AfterMapping
    default void addLinks(@MappingTarget GrupoResponse grupoResponse) {
        grupoResponse.add(linkTo(methodOn(GrupoController.class)
                .buscar(grupoResponse.getId())).withSelfRel());

        grupoResponse.add(linkTo(methodOn(GrupoController.class)
                .listar()).withRel("grupos"));
    }

    @Override
    default CollectionModel<GrupoResponse> toCollectionModel(Iterable<? extends Grupo> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(GrupoController.class).listar()).withSelfRel());
    }
}