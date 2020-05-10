package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToFotoProduto;
import static com.algaworks.algafood.api.AlgaLinks.linkToProduto;

@Mapper
public abstract class FotoProdutoMapper implements RepresentationModelAssembler<FotoProduto, FotoProdutoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    @Mappings({
            @Mapping(target = "contentType", source = "arquivo.contentType"),
            @Mapping(target = "tamanho", source = "arquivo.size"),
            @Mapping(target = "nomeArquivo", source = "arquivo.originalFilename")
    })
    public abstract FotoProduto toDomain(FotoProdutoRequest request);

    @AfterMapping
    protected void addLinks(@MappingTarget FotoProdutoResponse fotoResponse, FotoProduto fotoProduto) {

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            fotoResponse.add(
                    linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));

            fotoResponse.add(
                    linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
        }
    }
}