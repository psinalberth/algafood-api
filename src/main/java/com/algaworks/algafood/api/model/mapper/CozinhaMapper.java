package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.CozinhaIdRequest;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.linkToCozinha;
import static com.algaworks.algafood.api.AlgaLinks.linkToCozinhas;

@Mapper
public abstract class CozinhaMapper implements RepresentationModelAssembler<Cozinha, CozinhaResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Cozinha toDomain(CozinhaRequest request);

    public abstract Cozinha toDomain(CozinhaIdRequest request);

    public abstract Cozinha toDomainCopy(@MappingTarget Cozinha cozinha, CozinhaRequest request);

    public abstract List<CozinhaResponse> toCollectionModel(List<Cozinha> cozinhas);

    @AfterMapping
    protected void addLinks(@MappingTarget CozinhaResponse cozinhaResponse) {
        cozinhaResponse.add(linkToCozinha(cozinhaResponse.getId()));

        if (algafoodSecurity.podeConsultarCozinhas()) {
            cozinhaResponse.add(linkToCozinhas("cozinhas"));
        }
    }

    @Override
    public CollectionModel<CozinhaResponse> toCollectionModel(Iterable<? extends Cozinha> entities) {
        CollectionModel<CozinhaResponse> cozinhas = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarCozinhas()) {
            cozinhas.add(linkToCozinhas());
        }

        return cozinhas;
    }
}