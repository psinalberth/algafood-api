package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.CidadeResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesResponse")
@Getter
@Setter
public class CidadesOpenApi {

    private CidadeEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedResponse")
    @Getter
    @Setter
    public class CidadeEmbeddedOpenApi {
        private List<CidadeResponse> cidades;
    }
}