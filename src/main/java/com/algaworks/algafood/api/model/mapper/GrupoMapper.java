package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.domain.model.Grupo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.*;

@Mapper
public interface GrupoMapper extends RepresentationModelAssembler<Grupo, GrupoResponse> {

    Grupo toDomain(GrupoRequest request);

    Grupo toDomainCopy(@MappingTarget Grupo grupo, GrupoRequest request);

    @Mapping(target = "id", source = "grupoId")
    Grupo map(Long grupoId);

    GrupoResponse toModel(Grupo grupo);

    @AfterMapping
    default void addLinks(@MappingTarget GrupoResponse grupoResponse) {
        grupoResponse.add(linkToGrupo(grupoResponse.getId()));
        grupoResponse.add(linkToGrupos("grupos"));
        grupoResponse.add(linkToGrupoPermissoes(grupoResponse.getId(), "permissoes"));
    }

    @Override
    default CollectionModel<GrupoResponse> toCollectionModel(Iterable<? extends Grupo> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkToGrupos());
    }
}