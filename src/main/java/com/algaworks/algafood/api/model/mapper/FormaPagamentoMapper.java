package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.FormaPagamentoIdRequest;
import com.algaworks.algafood.api.model.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static com.algaworks.algafood.api.AlgaLinks.linkToFormaPagamento;
import static com.algaworks.algafood.api.AlgaLinks.linkToFormasPagamento;

@Mapper
public abstract class FormaPagamentoMapper implements RepresentationModelAssembler<FormaPagamento, FormaPagamentoResponse> {

    @Autowired
    private AlgafoodSecurity algafoodSecurity;

    public abstract FormaPagamento toDomain(FormaPagamentoRequest request);

    public abstract FormaPagamento toDomain(FormaPagamentoIdRequest request);

    public abstract FormaPagamento toDomainCopy(@MappingTarget FormaPagamento formaPagamento, FormaPagamentoRequest request);

    @AfterMapping
    protected void addLinks(@MappingTarget FormaPagamentoResponse formaPagamentoResponse) {
        formaPagamentoResponse.add(linkToFormaPagamento(formaPagamentoResponse.getId()));

        if (algafoodSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoResponse.add(linkToFormasPagamento("formasPagamento"));
        }
    }

    @Override
    public CollectionModel<FormaPagamentoResponse> toCollectionModel(Iterable<? extends FormaPagamento> entities) {

        CollectionModel<FormaPagamentoResponse> formasPagamento =
                RepresentationModelAssembler.super.toCollectionModel(entities);

        if (algafoodSecurity.podeConsultarFormasPagamento()) {
            formasPagamento.add(linkToFormasPagamento());
        }

        return formasPagamento;
    }
}