package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import com.algaworks.algafood.domain.model.Restaurante;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        CozinhaMapper.class
})
public interface RestauranteResumidoMapper extends
        RepresentationModelAssembler<Restaurante, RestauranteResumidoResponse> {

    @AfterMapping
    default void addLinks(@MappingTarget RestauranteResumidoResponse restauranteResponse) {
        restauranteResponse.add(linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteResponse.getId())).withSelfRel());

        restauranteResponse.add(linkTo(methodOn(RestauranteController.class)
                .listar()).withRel("restaurantes"));
    }

    @Override
    default CollectionModel<RestauranteResumidoResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(RestauranteController.class).listar()).withSelfRel());
    }
}