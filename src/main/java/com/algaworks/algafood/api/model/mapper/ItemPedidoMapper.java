package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.model.request.ItemPedidoRequest;
import com.algaworks.algafood.api.model.response.ItemPedidoResponse;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.mapstruct.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        ProdutoMapper.class
})
public interface ItemPedidoMapper extends RepresentationModelAssembler<ItemPedido, ItemPedidoResponse> {

    @Mapping(target = "produto", source = "produtoId")
    ItemPedido toDomain(ItemPedidoRequest request);

    List<ItemPedido> toDomainCollection(List<ItemPedidoRequest> itensRequest);

    @Mappings({
            @Mapping(target = "produtoId", source = "produto.id"),
            @Mapping(target = "nomeProduto", source = "produto.nome")
    })
    ItemPedidoResponse toModel(ItemPedido itemPedido);

    List<ItemPedidoResponse> toCollectionModel(List<ItemPedido> itensPedido);

    @AfterMapping
    default void addLinks(@MappingTarget ItemPedidoResponse itemPedidoResponse, ItemPedido itemPedido) {
        itemPedidoResponse.add(linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(itemPedido.getPedido().getRestaurante().getId(), itemPedidoResponse.getProdutoId())).withRel("produto"));
    }

    @Override
    default CollectionModel<ItemPedidoResponse> toCollectionModel(Iterable<? extends ItemPedido> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(RestauranteController.class).listar()).withSelfRel());
    }
}