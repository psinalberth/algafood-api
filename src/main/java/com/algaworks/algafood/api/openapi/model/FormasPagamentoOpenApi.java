package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasPagamentoResponse")
@Getter
@Setter
public class FormasPagamentoOpenApi {

    private FormaPagamentoEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("FormasPagamentoEmbeddedResponse")
    @Getter
    @Setter
    public class FormaPagamentoEmbeddedOpenApi {
        private List<FormaPagamentoResponse> formasPagamento;
    }
}