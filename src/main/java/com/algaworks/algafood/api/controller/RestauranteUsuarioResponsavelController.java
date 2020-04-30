package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.UsuarioMapper;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
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

    public RestauranteUsuarioResponsavelController(RestauranteService restauranteService, UsuarioMapper usuarioMapper) {
        this.restauranteService = restauranteService;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    @GetMapping
    public CollectionModel<UsuarioResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        CollectionModel<UsuarioResponse> usuariosResponse =
                usuarioMapper.toCollectionModel(restaurante.getResponsaveis());

        usuariosResponse.getContent().forEach(usuario -> {
            usuario.add(linkToRestauranteResponsavelDesassociacao(
                    restauranteId, usuario.getId(), "desassociar")
            );
        });

        return usuariosResponse.removeLinks()
                .add(linkToRestauranteResponsaveis(restauranteId))
                .add(linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
    }

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