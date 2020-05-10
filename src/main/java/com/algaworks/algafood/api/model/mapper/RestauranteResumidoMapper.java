package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurante;
import static com.algaworks.algafood.api.AlgaLinks.linkToRestaurantes;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        CozinhaMapper.class
})
public abstract class RestauranteResumidoMapper implements
        RepresentationModelAssembler<Restaurante, RestauranteResumidoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    @AfterMapping
    protected void addLinks(@MappingTarget RestauranteResumidoResponse restauranteResponse) {
        restauranteResponse.add(linkToRestaurante(restauranteResponse.getId()));

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            restauranteResponse.add(linkToRestaurantes("restaurantes"));
        }
    }

    @Override
    public CollectionModel<RestauranteResumidoResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteResumidoResponse> restaurantes = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            restaurantes.add(linkToRestaurantes());
        }

        return  restaurantes;
    }
}