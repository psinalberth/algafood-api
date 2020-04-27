package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.domain.model.Grupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper
public interface GrupoMapper {

    Grupo toDomain(GrupoRequest request);

    Grupo toDomainCopy(@MappingTarget Grupo grupo, GrupoRequest request);

    @Mapping(target = "id", source = "grupoId")
    Grupo map(Long grupoId);

    GrupoResponse toModel(Grupo grupo);

    List<GrupoResponse> toCollectionModel(Collection<Grupo> grupos);
}