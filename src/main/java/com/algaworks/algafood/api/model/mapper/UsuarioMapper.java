package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.UsuarioRequest;
import com.algaworks.algafood.api.model.request.UsuarioSenhaRequest;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.*;

@Mapper
public abstract class UsuarioMapper implements RepresentationModelAssembler<Usuario, UsuarioResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Usuario toDomain(UsuarioRequest request);

    public abstract Usuario toDomain(UsuarioSenhaRequest request);

    public abstract Usuario toDomainCopy(@MappingTarget Usuario usuario, UsuarioRequest request);

    @Mapping(target = "id", source = "usuarioId")
    public abstract Usuario map(Long usuarioId);

    @AfterMapping
    protected void addLinks(@MappingTarget UsuarioResponse usuarioResponse) {
        usuarioResponse.add(linkToUsuario(usuarioResponse.getId()));

        if (algafoodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioResponse.add(linkToUsuarios("usuarios"));
            usuarioResponse.add(linkToUsuarioGrupos(usuarioResponse.getId(), "grupos"));
        }
    }

    @Override
    public CollectionModel<UsuarioResponse> toCollectionModel(Iterable<? extends Usuario> entities) {
        CollectionModel<UsuarioResponse> usuarios = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarios.add(linkToUsuarios());
        }

        return usuarios;
    }
}