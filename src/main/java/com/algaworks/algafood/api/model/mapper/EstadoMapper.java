package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.EstadoIdRequest;
import com.algaworks.algafood.api.model.request.EstadoRequest;
import com.algaworks.algafood.api.model.response.EstadoResponse;
import com.algaworks.algafood.domain.model.Estado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface EstadoMapper {

    Estado toModel(EstadoRequest request);

    Estado mapIdToModel(EstadoIdRequest request);

    @Mapping(target = "id", source = "estadoId")
    Estado map(Long estadoId);

    Estado toModelCopy(@MappingTarget Estado estado, EstadoRequest request);

    EstadoResponse toResponse(Estado estado);

    List<EstadoResponse> toCollectionResponse(List<Estado> estados);
}