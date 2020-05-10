package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.PedidoRequest;
import com.algaworks.algafood.api.model.response.PedidoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
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
public abstract class PedidoMapper implements RepresentationModelAssembler<Pedido, PedidoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Pedido toDomain(PedidoRequest request);

    public abstract List<PedidoResponse> toCollectionModel(List<Pedido> pedidos);

    @AfterMapping
    protected void addLinks(@MappingTarget PedidoResponse pedidoResponse, Pedido pedido) {
        pedidoResponse.add(linkToPedido(pedidoResponse.getCodigo()));

        if (algafoodSecurity.podeGerenciarPedidos(pedidoResponse.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoResponse.add(linkToConfirmacaoPedido(pedidoResponse.getCodigo(), "confirmar"));
            }

            if (pedido.podeSerEntregue()) {
                pedidoResponse.add(linkToEntregaPedido(pedidoResponse.getCodigo(), "entregar"));
            }

            if (pedido.podeSerCancelado()) {
                pedidoResponse.add(linkToCancelamentoPedido(pedidoResponse.getCodigo(), "cancelar"));
            }
        }

        if (algafoodSecurity.podePesquisarPedidos()) {
            pedidoResponse.add(linkToPedidos("pedidos"));
        }
    }
}