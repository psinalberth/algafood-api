package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.RestauranteIdRequest;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import com.algaworks.algafood.domain.model.Restaurante;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        CozinhaMapper.class,
        EnderecoMapper.class
})
public interface RestauranteMapper {

    Restaurante toModel(RestauranteRequest request);

    Restaurante mapIdToModel(RestauranteIdRequest request);

    @InheritConfiguration(name = "toModel")
    Restaurante toModelCopy(@MappingTarget Restaurante restaurante, RestauranteRequest request);

    @Mapping(target = "id", source = "restauranteId")
    Restaurante map(Long restauranteId);

    RestauranteResponse toResponse(Restaurante restaurante);

    RestauranteResumidoResponse toFilteredResponse(Restaurante restaurante);

    List<RestauranteResponse> toCollectionResponse(List<Restaurante> restaurantes);

    List<RestauranteResumidoResponse> toFilteredCollectionResponse(List<Restaurante> restaurantes);
}