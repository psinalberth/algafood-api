package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.PermissaoRequest;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import com.algaworks.algafood.domain.model.Permissao;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToPermissoes;

@Mapper
public interface PermissaoMapper extends RepresentationModelAssembler<Permissao, PermissaoResponse> {

    Permissao toDomain(PermissaoRequest request);

    Permissao toDomainCopy(@MappingTarget Permissao permissao, PermissaoRequest request);

    @Override
    default CollectionModel<PermissaoResponse> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkToPermissoes());
    }
}