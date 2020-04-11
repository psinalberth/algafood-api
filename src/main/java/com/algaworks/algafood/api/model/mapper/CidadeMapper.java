package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.CidadeIdRequest;
import com.algaworks.algafood.api.model.request.CidadeRequest;
import com.algaworks.algafood.api.model.response.CidadeResponse;
import com.algaworks.algafood.domain.model.Cidade;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        EstadoMapper.class
})
public interface CidadeMapper {

    @Mapping(target = "estado", source = "estado")
    Cidade toModel(CidadeRequest request);

    Cidade mapIdToModel(CidadeIdRequest request);

    @InheritConfiguration(name = "toModel")
    Cidade toModelCopy(@MappingTarget Cidade cidade, CidadeRequest request);

    @Mapping(target = "id", source = "cidadeId")
    Cidade map(Long cidadeId);

    CidadeResponse toResponse(Cidade cidade);

    List<CidadeResponse> toCollectionResponse(List<Cidade> cidades);
}