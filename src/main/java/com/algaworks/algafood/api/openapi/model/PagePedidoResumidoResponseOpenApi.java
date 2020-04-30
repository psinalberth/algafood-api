package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.PedidoResumidoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumidosResponse")
@Getter
@Setter
public class PagePedidoResumidoResponseOpenApi {

    private PedidoEmbeddedOpenApi _embedded;
    private Links _links;
    private PageOpenApi page;

    @ApiModel("PedidosEmbeddedResponse")
    @Getter
    @Setter
    public class PedidoEmbeddedOpenApi {
        private List<PedidoResumidoResponse> pedidos;
    }
}