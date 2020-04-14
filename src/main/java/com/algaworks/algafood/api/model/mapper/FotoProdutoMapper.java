package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface FotoProdutoMapper {

    @Mappings({
            @Mapping(target = "contentType", source = "arquivo.contentType"),
            @Mapping(target = "tamanho", source = "arquivo.size"),
            @Mapping(target = "nomeArquivo", source = "arquivo.originalFilename")
    })
    FotoProduto toModel(FotoProdutoRequest request);

    FotoProdutoResponse toResponse(FotoProduto fotoProduto);
}