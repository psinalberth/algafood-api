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

    Grupo toModel(GrupoRequest request);

    Grupo toModelCopy(@MappingTarget Grupo grupo, GrupoRequest request);

    @Mapping(target = "id", source = "grupoId")
    Grupo map(Long grupoId);

    GrupoResponse toResponse(Grupo grupo);

    List<GrupoResponse> toCollectionResponse(Collection<Grupo> grupos);
}