package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.ItemPedidoRequest;
import com.algaworks.algafood.api.model.response.ItemPedidoResponse;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.mapstruct.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.linkToProduto;

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
        itemPedidoResponse.add(linkToProduto(
                itemPedido.getPedido().getRestaurante().getId(),
                itemPedido.getProduto().getId(),
                "produto")
        );
    }
}