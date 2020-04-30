package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.PedidoRequest;
import com.algaworks.algafood.api.model.response.PedidoResponse;
import com.algaworks.algafood.domain.model.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteResumidoMapper.class,
        UsuarioMapper.class,
        FormaPagamentoMapper.class,
        EnderecoMapper.class,
        ItemPedidoMapper.class
})
public interface PedidoMapper extends RepresentationModelAssembler<Pedido, PedidoResponse> {

    Pedido toDomain(PedidoRequest request);

    List<PedidoResponse> toCollectionModel(List<Pedido> pedidos);

    @AfterMapping
    default void addLinks(@MappingTarget PedidoResponse pedidoResponse, Pedido pedido) {
        pedidoResponse.add(linkToPedido(pedidoResponse.getCodigo()));

        if (pedido.podeSerConfirmado()) {
            pedidoResponse.add(linkToConfirmacaoPedido(pedidoResponse.getCodigo(), "confirmar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoResponse.add(linkToEntregaPedido(pedidoResponse.getCodigo(), "entregar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoResponse.add(linkToCancelamentoPedido(pedidoResponse.getCodigo(), "cancelar"));
        }

        pedidoResponse.add(linkToPedidos("pedidos"));
    }
}