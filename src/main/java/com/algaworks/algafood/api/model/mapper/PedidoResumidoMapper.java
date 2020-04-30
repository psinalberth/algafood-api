package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.response.PedidoResumidoResponse;
import com.algaworks.algafood.domain.model.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToPedidos;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteResumidoMapper.class,
        UsuarioMapper.class
})
public interface PedidoResumidoMapper extends RepresentationModelAssembler<Pedido, PedidoResumidoResponse> {

    @AfterMapping
    default void addLinks(@MappingTarget PedidoResumidoResponse pedidoResponse) {
        pedidoResponse.add(linkToPedido(pedidoResponse.getCodigo()));
        pedidoResponse.add(linkToPedidos("pedidos"));
    }

    @Override
    default CollectionModel<PedidoResumidoResponse> toCollectionModel(Iterable<? extends Pedido> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkToPedidos("pedidos"));
    }
}