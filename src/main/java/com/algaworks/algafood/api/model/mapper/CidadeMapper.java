package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.model.request.CidadeIdRequest;
import com.algaworks.algafood.api.model.request.CidadeRequest;
import com.algaworks.algafood.api.model.response.CidadeResponse;
import com.algaworks.algafood.domain.model.Cidade;
import org.mapstruct.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        EstadoMapper.class
})
public interface CidadeMapper extends RepresentationModelAssembler<Cidade, CidadeResponse> {

    @Mapping(target = "estado", source = "estado")
    Cidade toDomain(CidadeRequest request);

    Cidade toDomain(CidadeIdRequest request);

    @InheritConfiguration(name = "toDomain")
    Cidade toDomainCopy(@MappingTarget Cidade cidade, CidadeRequest request);

    List<CidadeResponse> toCollectionModel(List<Cidade> cidades);

    @AfterMapping
    default void addLinks(@MappingTarget CidadeResponse cidadeResponse) {
        cidadeResponse.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeResponse.getId())).withSelfRel());

        cidadeResponse.add(linkTo(methodOn(CidadeController.class)
                .listar(null)).withRel("cidades"));
    }

    @Override
    default CollectionModel<CidadeResponse> toCollectionModel(Iterable<? extends Cidade> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(CidadeController.class).listar(null)).withSelfRel());
    }
}