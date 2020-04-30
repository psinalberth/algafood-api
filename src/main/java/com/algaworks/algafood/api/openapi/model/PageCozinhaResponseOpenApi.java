package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.CozinhaResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasResponse")
@Getter
@Setter
public class PageCozinhaResponseOpenApi {

    private CozinhaEmbeddedOpenApi _embedded;
    private Links _links;
    private PageOpenApi page;

    @ApiModel("CozinhasEmbeddedResponse")
    @Getter
    @Setter
    public class CozinhaEmbeddedOpenApi {
        private List<CozinhaResponse> cozinhas;
    }
}