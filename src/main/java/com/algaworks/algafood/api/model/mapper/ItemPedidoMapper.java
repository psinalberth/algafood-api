package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.ItemPedidoRequest;
import com.algaworks.algafood.api.model.response.ItemPedidoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.linkToProduto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        ProdutoMapper.class
})
public abstract class ItemPedidoMapper implements RepresentationModelAssembler<ItemPedido, ItemPedidoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    @Mapping(target = "produto", source = "produtoId")
    public abstract ItemPedido toDomain(ItemPedidoRequest request);

    public abstract List<ItemPedido> toDomainCollection(List<ItemPedidoRequest> itensRequest);

    @Mappings({
            @Mapping(target = "produtoId", source = "produto.id"),
            @Mapping(target = "nomeProduto", source = "produto.nome")
    })
    public abstract ItemPedidoResponse toModel(ItemPedido itemPedido);

    public abstract List<ItemPedidoResponse> toCollectionModel(List<ItemPedido> itensPedido);

    @AfterMapping
    protected void addLinks(@MappingTarget ItemPedidoResponse itemPedidoResponse, ItemPedido itemPedido) {
        if (algafoodSecurity.podeConsultarRestaurantes()) {
            itemPedidoResponse.add(linkToProduto(
                    itemPedido.getPedido().getRestaurante().getId(),
                    itemPedido.getProduto().getId(),
                    "produto")
            );
        }
    }
}