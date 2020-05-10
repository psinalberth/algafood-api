package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.RestauranteIdRequest;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        CozinhaMapper.class,
        EnderecoMapper.class
})
public abstract class RestauranteMapper implements RepresentationModelAssembler<Restaurante, RestauranteResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Restaurante toDomain(RestauranteRequest request);

    public abstract Restaurante toDomain(RestauranteIdRequest request);

    @InheritConfiguration(name = "toDomain")
    public abstract Restaurante toDomainCopy(@MappingTarget Restaurante restaurante, RestauranteRequest request);

    @Mapping(target = "id", source = "restauranteId")
    public abstract Restaurante map(Long restauranteId);

    public abstract List<RestauranteResponse> toCollectionModel(List<Restaurante> restaurantes);

    @AfterMapping
    protected void addLinks(@MappingTarget RestauranteResponse restauranteResponse, Restaurante restaurante) {
        restauranteResponse.add(linkToRestaurante(restaurante.getId()));

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            restauranteResponse.add(linkToProdutos(restaurante.getId(), "produtos"));
            restauranteResponse.add(linkToRestauranteFormasPagamento(restaurante.getId(), "formasPagamento"));
            restauranteResponse.add(linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
            restauranteResponse.add(linkToRestaurantes("restaurantes"));
        }

        if (algafoodSecurity.podeGerenciarCadastroRestaurantes()) {
            if (!restaurante.isAtivo()) {
                restauranteResponse.add(linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            } else {
                restauranteResponse.add(linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (algafoodSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.isAberturaPermitida()) {
                restauranteResponse.add(linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.isAberto()) {
                restauranteResponse.add(linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }
    }

    @Override
    public CollectionModel<RestauranteResponse> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteResponse> restaurantes = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            restaurantes.add(linkToRestaurantes());
        }

        return  restaurantes;
    }
}