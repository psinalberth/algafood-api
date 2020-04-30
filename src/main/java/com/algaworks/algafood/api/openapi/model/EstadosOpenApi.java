package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.EstadoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("EstadosResponse")
@Getter
@Setter
public class EstadosOpenApi {

    private EstadoEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("EstadosEmbeddedResponse")
    @Getter
    @Setter
    public class EstadoEmbeddedOpenApi {
        private List<EstadoResponse> estados;
    }
}