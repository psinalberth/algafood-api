package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.RestauranteIdRequest;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.domain.model.Restaurante;
import org.mapstruct.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.*;

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
    default void addLinks(@MappingTarget RestauranteResponse restauranteResponse, Restaurante restaurante) {
        restauranteResponse.add(linkToRestaurante(restaurante.getId()));
        restauranteResponse.add(linkToProdutos(restaurante.getId(), "produtos"));
        restauranteResponse.add(linkToRestauranteFormasPagamento(restaurante.getId(), "formasPagamento"));
        restauranteResponse.add(linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));

        if (!restaurante.isAtivo()) {
            restauranteResponse.add(linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        } else {
            restauranteResponse.add(linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.isAberturaPermitida()) {
            restauranteResponse.add(linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.isAberto()) {
            restauranteResponse.add(linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        restauranteResponse.add(linkToRestaurantes("restaurantes"));
    }

    @Override
    default CollectionModel<RestauranteResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkToRestaurantes());
    }
}