package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.UsuarioRequest;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface UsuarioMapper {

    Usuario toModel(UsuarioRequest request);

    Usuario toModelCopy(@MappingTarget Usuario usuario, UsuarioRequest request);

    @Mapping(target = "id", source = "usuarioId")
    Usuario map(Long usuarioId);

    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toCollectionResponse(List<Usuario> usuarios);
}