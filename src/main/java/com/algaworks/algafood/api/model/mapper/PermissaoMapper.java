package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.PermissaoRequest;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Permissao;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToPermissoes;

@Mapper
public abstract class PermissaoMapper implements RepresentationModelAssembler<Permissao, PermissaoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Permissao toDomain(PermissaoRequest request);

    public abstract Permissao toDomainCopy(@MappingTarget Permissao permissao, PermissaoRequest request);

    @Override
    public CollectionModel<PermissaoResponse> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoResponse> permissoes = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            permissoes.add(linkToPermissoes());
        }

        return permissoes;
    }
}