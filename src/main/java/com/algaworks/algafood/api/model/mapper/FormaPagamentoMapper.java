package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.model.request.FormaPagamentoIdRequest;
import com.algaworks.algafood.api.model.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper
public interface FormaPagamentoMapper extends RepresentationModelAssembler<FormaPagamento, FormaPagamentoResponse> {

    FormaPagamento toDomain(FormaPagamentoRequest request);

    FormaPagamento toDomain(FormaPagamentoIdRequest request);

    FormaPagamento toDomainCopy(@MappingTarget FormaPagamento formaPagamento, FormaPagamentoRequest request);

    @AfterMapping
    default void addLinks(@MappingTarget FormaPagamentoResponse formaPagamentoResponse) {
        formaPagamentoResponse.add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoResponse.getId(), null)).withSelfRel());

        formaPagamentoResponse.add(linkTo(methodOn(FormaPagamentoController.class)
                .listar(null)).withRel("formasPagamento"));
    }

    @Override
    default CollectionModel<FormaPagamentoResponse> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(FormaPagamentoController.class).listar(null)).withSelfRel());
    }
}