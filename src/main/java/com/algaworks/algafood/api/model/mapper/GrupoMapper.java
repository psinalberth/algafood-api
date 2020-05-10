package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.*;

@Mapper
public abstract class GrupoMapper implements RepresentationModelAssembler<Grupo, GrupoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Grupo toDomain(GrupoRequest request);

    public abstract Grupo toDomainCopy(@MappingTarget Grupo grupo, GrupoRequest request);

    @Mapping(target = "id", source = "grupoId")
    public abstract Grupo map(Long grupoId);

    @AfterMapping
    protected void addLinks(@MappingTarget GrupoResponse grupoResponse) {
        grupoResponse.add(linkToGrupo(grupoResponse.getId()));

        if (algafoodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoResponse.add(linkToGrupos("grupos"));
            grupoResponse.add(linkToGrupoPermissoes(grupoResponse.getId(), "permissoes"));
        }
    }

    @Override
    public CollectionModel<GrupoResponse> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoResponse> grupos = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupos.add(linkToGrupos());
        }

        return grupos;
    }
}