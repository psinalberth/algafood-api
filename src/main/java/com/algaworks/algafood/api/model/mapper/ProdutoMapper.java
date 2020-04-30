package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.request.ProdutoRequest;
import com.algaworks.algafood.api.model.response.ProdutoResponse;
import com.algaworks.algafood.domain.model.Produto;
import org.mapstruct.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteMapper.class
})
public interface ProdutoMapper extends RepresentationModelAssembler<Produto, ProdutoResponse> {

    Produto toDomain(ProdutoRequest request);

    @Mapping(target = "restaurante", source = "restauranteId")
    Produto toDomain(Long restauranteId, ProdutoRequest request);

    Produto toDomainCopy(@MappingTarget Produto produto, ProdutoRequest request);

    @Mapping(target = "id", source = "produtoId")
    Produto map(Long produtoId);

    List<Produto> toDomainCollectionc(List<ProdutoRequest> produtos);

    @AfterMapping
    default void addLinks(@MappingTarget ProdutoResponse produtoResponse, Produto produto) {
        produtoResponse.add(linkToProduto(produto.getRestaurante().getId(), produto.getId()));
        produtoResponse.add(linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
        produtoResponse.add(linkToProdutos(produto.getRestaurante().getId(), "produtos"));
    }
}