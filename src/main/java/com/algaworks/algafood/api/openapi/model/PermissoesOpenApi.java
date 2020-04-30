package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.PermissaoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissoesResponse")
@Getter
@Setter
public class PermissoesOpenApi {

    private PermissaoEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("PermissoesEmbeddedResponse")
    @Getter
    @Setter
    public class PermissaoEmbeddedOpenApi {
        private List<PermissaoResponse> permissoes;
    }
}