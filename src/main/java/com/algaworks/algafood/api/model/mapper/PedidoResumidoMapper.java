package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.response.PedidoResumidoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToPedido;
import static com.algaworks.algafood.api.AlgaLinks.linkToPedidos;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteResumidoMapper.class,
        UsuarioMapper.class
})
public abstract class PedidoResumidoMapper implements RepresentationModelAssembler<Pedido, PedidoResumidoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    @AfterMapping
    protected void addLinks(@MappingTarget PedidoResumidoResponse pedidoResponse) {
        pedidoResponse.add(linkToPedido(pedidoResponse.getCodigo()));

        if (algafoodSecurity.podePesquisarPedidos()) {
            pedidoResponse.add(linkToPedidos("pedidos"));
        }
    }

    @Override
    public CollectionModel<PedidoResumidoResponse> toCollectionModel(Iterable<? extends Pedido> entities) {
        CollectionModel<PedidoResumidoResponse> pedidos = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podePesquisarPedidos()) {
            pedidos.add(linkToPedidos("self"));
        }

        return pedidos;
    }
}