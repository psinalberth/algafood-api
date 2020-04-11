package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.CozinhaIdRequest;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import com.algaworks.algafood.domain.model.Cozinha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CozinhaMapper {

    Cozinha toModel(CozinhaRequest request);

    Cozinha mapIdToModel(CozinhaIdRequest request);

    Cozinha toModelCopy(@MappingTarget Cozinha cozinha, CozinhaRequest request);

    @Mapping(target = "id", source = "cozinhaId")
    Cozinha map(Long cozinhaId);

    CozinhaResponse toResponse(Cozinha cozinha);

    List<CozinhaResponse> toCollectionResponse(List<Cozinha> cozinhas);
}