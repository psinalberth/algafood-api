package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.request.UsuarioRequest;
import com.algaworks.algafood.api.model.request.UsuarioSenhaRequest;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.domain.model.Usuario;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Mapper
public interface UsuarioMapper extends RepresentationModelAssembler<Usuario, UsuarioResponse> {

    Usuario toDomain(UsuarioRequest request);

    Usuario toDomain(UsuarioSenhaRequest request);

    Usuario toDomainCopy(@MappingTarget Usuario usuario, UsuarioRequest request);

    @Mapping(target = "id", source = "usuarioId")
    Usuario map(Long usuarioId);

    @AfterMapping
    default void addLinks(@MappingTarget UsuarioResponse response) {
        response.add(linkTo(methodOn(UsuarioController.class)
                .buscar(response.getId())).withSelfRel());

        response.add(linkTo(methodOn(UsuarioController.class)
                .listar()).withRel("usuarios"));
    }

    @Override
    default CollectionModel<UsuarioResponse> toCollectionModel(Iterable<? extends Usuario> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());
    }
}