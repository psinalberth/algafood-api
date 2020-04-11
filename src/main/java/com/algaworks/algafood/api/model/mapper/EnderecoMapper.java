package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.EnderecoRequest;
import com.algaworks.algafood.api.model.response.EnderecoResponse;
import com.algaworks.algafood.domain.model.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        CidadeMapper.class
})
public interface EnderecoMapper {

    Endereco toModel(EnderecoRequest request);

    @InheritConfiguration(name = "toModel")
    Endereco toModelCopy(@MappingTarget Endereco endereco, EnderecoRequest request);

    EnderecoResponse toResponse(Endereco endereco);
}