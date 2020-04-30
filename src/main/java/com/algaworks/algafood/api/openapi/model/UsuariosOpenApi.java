package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.UsuarioResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsuariosResponse")
@Getter
@Setter
public class UsuariosOpenApi {

    private UsuarioEmbeddedOpenApi _embedded;
    private Links _links;

    @ApiModel("UsuariosEmbeddedResponse")
    @Getter
    @Setter
    public class UsuarioEmbeddedOpenApi {
        private List<UsuarioResponse> usuarios;
    }
}