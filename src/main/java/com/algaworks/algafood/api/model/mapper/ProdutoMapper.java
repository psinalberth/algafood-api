package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.ProdutoRequest;
import com.algaworks.algafood.api.model.response.ProdutoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Produto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        RestauranteMapper.class
})
public abstract class ProdutoMapper implements RepresentationModelAssembler<Produto, ProdutoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Produto toDomain(ProdutoRequest request);

    public abstract Produto toDomainCopy(@MappingTarget Produto produto, ProdutoRequest request);

    @Mapping(target = "id", source = "produtoId")
    public abstract Produto map(Long produtoId);

    public abstract List<Produto> toDomainCollectionc(List<ProdutoRequest> produtos);

    @AfterMapping
    protected void addLinks(@MappingTarget ProdutoResponse produtoResponse, Produto produto) {
        produtoResponse.add(linkToProduto(produto.getRestaurante().getId(), produto.getId()));

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            produtoResponse.add(linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
            produtoResponse.add(linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        }
    }
}