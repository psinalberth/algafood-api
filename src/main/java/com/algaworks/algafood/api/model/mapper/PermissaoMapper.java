package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.PermissaoRequest;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import com.algaworks.algafood.domain.model.Permissao;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PermissaoMapper {

    Permissao toDomain(PermissaoRequest request);

    Permissao toDomainCopy(@MappingTarget Permissao permissao, PermissaoRequest request);

    PermissaoResponse toModel(Permissao permissao);

    List<PermissaoResponse> toCollectionModel(Collection<Permissao> permissoes);
}