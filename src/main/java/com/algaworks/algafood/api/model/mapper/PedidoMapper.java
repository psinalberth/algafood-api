package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.AtualizacaoPedidoController;
import com.algaworks.algafood.api.controller.CadastroPedidoController;
import com.algaworks.algafood.api.model.request.PedidoRequest;
import com.algaworks.algafood.api.model.response.PedidoResponse;
import com.algaworks.algafood.domain.model.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        var urlPedidos = linkTo(methodOn(CadastroPedidoController.class)
                        .listar(null)).toUri().toString();

        var templateVariablesPaginacao = new TemplateVariables(
                new TemplateVariable("page", VariableType.REQUEST_PARAM),
                new TemplateVariable("size", VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", VariableType.REQUEST_PARAM)
        );

        var templateVariablesFiltro = new TemplateVariables(
                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)
        );

        pedidoResponse.add(linkTo(methodOn(CadastroPedidoController.class)
                .buscar(pedidoResponse.getCodigo())).withSelfRel());

        if (pedido.podeSerConfirmado()) {
            pedidoResponse.add(linkTo(methodOn(AtualizacaoPedidoController.class)
                    .confirmar(pedidoResponse.getCodigo())).withRel("confirmar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoResponse.add(linkTo(methodOn(AtualizacaoPedidoController.class)
                    .entregar(pedidoResponse.getCodigo())).withRel("entregar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoResponse.add(linkTo(methodOn(AtualizacaoPedidoController.class)
                    .cancelar(pedidoResponse.getCodigo())).withRel("cancelar"));
        }

        pedidoResponse.add(new Link(UriTemplate.of(urlPedidos,
                templateVariablesPaginacao.concat(templateVariablesFiltro)), "pedidos"));
    }
}