package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.UsuarioMapper;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.algaworks.algafood.api.AlgaLinks.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    final RestauranteService restauranteService;
    final UsuarioMapper usuarioMapper;
    final AlgafoodSecurity algafoodSecurity;

    public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, UsuarioMapper usuarioMapper,
                                                   AlgafoodSecurity algafoodSecurity) {
        this.restauranteService = restauranteService;
        this.usuarioMapper = usuarioMapper;
        this.algafoodSecurity = algafoodSecurity;
    }

    @SecurityConstants.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<UsuarioResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        CollectionModel<UsuarioResponse> usuariosResponse =
                usuarioMapper.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks();

        if (algafoodSecurity.podeGerenciarCadastroRestaurantes()) {
            usuariosResponse.getContent().forEach(usuario -> {
                usuario.add(linkToRestauranteResponsavelDesassociacao(
                        restauranteId, usuario.getId(), "desassociar")
                );
            });

            usuariosResponse.add(linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
        }

        usuariosResponse.add(linkToRestauranteResponsaveis(restauranteId));

        return usuariosResponse;
    }

    @SecurityConstants.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        try {
            restauranteService.associarResponsavel(restauranteId, usuarioId);
            return ResponseEntity.noContent().build();
        } catch (UsuarioNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @SecurityConstants.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        try {
            restauranteService.desassociarResponsavel(restauranteId, usuarioId);
            return ResponseEntity.noContent().build();
        } catch (UsuarioNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}