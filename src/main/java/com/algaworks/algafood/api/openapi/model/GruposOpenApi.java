package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.GrupoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposResponse")
@Getter
@Setter
public class GruposOpenApi {

    private GrupoEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("GruposEmbeddedResponse")
    @Getter
    @Setter
    public class GrupoEmbeddedOpenApi {
        private List<GrupoResponse> grupos;
    }
}