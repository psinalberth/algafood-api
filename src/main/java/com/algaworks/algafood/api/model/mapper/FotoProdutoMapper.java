package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.mapstruct.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToFotoProduto;
import static com.algaworks.algafood.api.AlgaLinks.linkToProduto;

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
        fotoResponse.add(
                linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

        fotoResponse.add(
                linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
    }
}