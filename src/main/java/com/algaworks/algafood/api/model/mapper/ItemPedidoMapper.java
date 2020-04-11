package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.ItemPedidoRequest;
import com.algaworks.algafood.api.model.response.ItemPedidoResponse;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        ProdutoMapper.class
})
public interface ItemPedidoMapper {

    @Mapping(target = "produto", source = "produtoId")
    ItemPedido toModel(ItemPedidoRequest request);

    List<ItemPedido> toCollectionMdel(List<ItemPedidoRequest> itensRequest);

    @Mappings({
            @Mapping(target = "produtoId", source = "produto.id"),
            @Mapping(target = "nomeProduto", source = "produto.nome")
    })
    ItemPedidoResponse toResponse(ItemPedido itemPedido);

    List<ItemPedidoResponse> toCollectionResponse(List<ItemPedido> itensPedido);
}