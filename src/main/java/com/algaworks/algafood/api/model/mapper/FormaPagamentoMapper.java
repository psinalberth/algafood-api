package com.algaworks.algafood.api.model.mapper;

import com.algaworks.algafood.api.model.request.FormaPagamentoIdRequest;
import com.algaworks.algafood.api.model.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface FormaPagamentoMapper {

    FormaPagamento toModel(FormaPagamentoRequest request);

    FormaPagamento mapIdToModel(FormaPagamentoIdRequest request);

    FormaPagamento toModelCopy(@MappingTarget FormaPagamento formaPagamento, FormaPagamentoRequest request);

    @Mapping(target = "id", source = "formaPagamentoId")
    FormaPagamento map(Long formaPagamentoId);

    FormaPagamentoResponse toResponse(FormaPagamento formaPagamento);

    List<FormaPagamentoResponse> toCollectionResponse(List<FormaPagamento> formasPagamento);
}