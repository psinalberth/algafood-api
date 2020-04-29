package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.request.ProdutoRequest;
import com.algaworks.algafood.api.model.response.ProdutoResponse;
import com.algaworks.algafood.domain.model.Produto;
import org.mapstruct.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

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
        produtoResponse.add(linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(produto.getRestaurante().getId(), produto.getId())).withSelfRel());

        produtoResponse.add(linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscar(produto.getRestaurante().getId(), produto.getId())).withRel("foto"));

        produtoResponse.add(linkTo(methodOn(RestauranteProdutoController.class)
                .listar(produto.getRestaurante().getId(), null)).withRel("produtos"));
    }
}