package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.ProdutoRequest;
import com.algaworks.algafood.api.model.response.ProdutoResponse;
import com.algaworks.algafood.domain.model.Produto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteMapper.class
})
public interface ProdutoMapper {

    Produto toModel(ProdutoRequest request);

    @Mapping(target = "restaurante", source = "restauranteId")
    Produto toModel(Long restauranteId, ProdutoRequest request);

    Produto toModellCopy(@MappingTarget Produto produto, ProdutoRequest request);

    @Mapping(target = "id", source = "produtoId")
    Produto map(Long produtoId);

    List<Produto> toCollectionModel(List<ProdutoRequest> produtos);

    ProdutoResponse toResponse(Produto produto);

    List<ProdutoResponse> toCollectionResponse(List<Produto> produtos);
}