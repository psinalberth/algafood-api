package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.model.request.RestauranteIdRequest;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.domain.model.Restaurante;
import org.mapstruct.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        CozinhaMapper.class,
        EnderecoMapper.class
})
public interface RestauranteMapper extends RepresentationModelAssembler<Restaurante, RestauranteResponse> {

    Restaurante toDomain(RestauranteRequest request);

    Restaurante toDomain(RestauranteIdRequest request);

    @InheritConfiguration(name = "toDomain")
    Restaurante toDomainCopy(@MappingTarget Restaurante restaurante, RestauranteRequest request);

    @Mapping(target = "id", source = "restauranteId")
    Restaurante map(Long restauranteId);

    List<RestauranteResponse> toCollectionModel(List<Restaurante> restaurantes);

    @AfterMapping
    default void addLinks(@MappingTarget RestauranteResponse restauranteResponse) {
        restauranteResponse.add(linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteResponse.getId())).withSelfRel());

        restauranteResponse.add(linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteResponse.getId(), true)).withRel("produtos"));

        restauranteResponse.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteResponse.getId())).withRel("formasPagamento"));

        restauranteResponse.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteResponse.getId())).withRel("responsaveis"));

        restauranteResponse.add(linkTo(methodOn(RestauranteController.class)
                .listar()).withRel("restaurantes"));
    }

    @Override
    default CollectionModel<RestauranteResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(RestauranteController.class).listar()).withSelfRel());
    }
}