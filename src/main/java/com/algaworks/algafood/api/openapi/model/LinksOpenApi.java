package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Links")
@Getter
@Setter
public class LinksOpenApi {

    private LinkOpenApi rel;

    @ApiModel("Link")
    @Getter
    @Setter
    private class LinkOpenApi {

        private String href;
        private boolean templated;
    }
}