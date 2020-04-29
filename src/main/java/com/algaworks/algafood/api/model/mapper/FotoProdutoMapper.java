package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.mapstruct.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface FotoProdutoMapper extends RepresentationModelAssembler<FotoProduto, FotoProdutoResponse> {

    @Mappings({
            @Mapping(target = "contentType", source = "arquivo.contentType"),
            @Mapping(target = "tamanho", source = "arquivo.size"),
            @Mapping(target = "nomeArquivo", source = "arquivo.originalFilename")
    })
    FotoProduto toDomain(FotoProdutoRequest request);

    @AfterMapping
    default void addLinks(@MappingTarget FotoProdutoResponse fotoResponse, FotoProduto fotoProduto) {

        fotoResponse.add(linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscar(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId())).withSelfRel());

        fotoResponse.add(linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId())).withRel("produto"));
    }
}