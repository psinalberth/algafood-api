package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.UsuarioMapper;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public List<UsuarioResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return usuarioMapper.toCollectionResponse(new ArrayList<>(restaurante.getResponsaveis()));
    }

    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        try {
            restauranteService.associarResponsavel(restauranteId, usuarioId);
        } catch (UsuarioNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        try {
            restauranteService.desassociarResponsavel(restauranteId, usuarioId);
        } catch (UsuarioNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}