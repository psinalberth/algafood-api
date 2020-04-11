package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.PedidoRequest;
import com.algaworks.algafood.api.model.response.PedidoResponse;
import com.algaworks.algafood.api.model.response.PedidoResumidoResponse;
import com.algaworks.algafood.domain.model.Pedido;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteMapper.class,
        UsuarioMapper.class,
        FormaPagamentoMapper.class,
        EnderecoMapper.class,
        ItemPedidoMapper.class
})
public interface PedidoMapper {

    Pedido toModel(PedidoRequest request);

    PedidoResponse toResponse(Pedido pedido);

    PedidoResumidoResponse toFilteredResponse(Pedido pedido);

    List<PedidoResponse> toCollectionResponse(List<Pedido> pedidos);

    List<PedidoResumidoResponse> toFilteredCollectionResponse(List<Pedido> pedidos);
}