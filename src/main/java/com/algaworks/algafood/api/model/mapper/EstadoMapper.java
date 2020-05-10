package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.EstadoIdRequest;
import com.algaworks.algafood.api.model.request.EstadoRequest;
import com.algaworks.algafood.api.model.response.EstadoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.Estado;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToEstado;
import static com.algaworks.algafood.api.AlgaLinks.linkToEstados;

@Mapper
public abstract class EstadoMapper implements RepresentationModelAssembler<Estado, EstadoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract Estado toDomain(EstadoRequest request);

    public abstract Estado toDomain(EstadoIdRequest request);

    public abstract Estado toDomainCopy(@MappingTarget Estado estado, EstadoRequest request);

    public abstract EstadoResponse toModel(Estado estado);

    @AfterMapping
    protected void addLinks(@MappingTarget EstadoResponse estadoResponse) {
        estadoResponse.add(linkToEstado(estadoResponse.getId()));

        if (algafoodSecurity.podeConsultarEstados()) {
            estadoResponse.add(linkToEstados("estados"));
        }
    }

    @Override
    public CollectionModel<EstadoResponse> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoResponse> estados = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarEstados()) {
            estados.add(linkToEstados());
        }

        return estados;
    }
}