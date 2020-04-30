package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.ProdutoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProdutosResponse")
@Getter
@Setter
public class ProdutosOpenApi {

    private ProdutoEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("ProdutosEmbeddedResponse")
    @Getter
    @Setter
    public class ProdutoEmbeddedOpenApi {
        private List<ProdutoResponse> produtos;
    }
}