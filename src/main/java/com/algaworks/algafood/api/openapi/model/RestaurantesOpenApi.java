package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantesResponse")
@Getter
@Setter
public class RestaurantesOpenApi {

    private RestauranteEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantesEmbeddedResponse")
    @Getter
    @Setter
    public class RestauranteEmbeddedOpenApi {
        private List<RestauranteResumidoResponse> restaurantes;
    }
}