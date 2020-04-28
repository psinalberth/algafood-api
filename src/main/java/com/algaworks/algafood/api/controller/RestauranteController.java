package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.RestauranteMapper;
import com.algaworks.algafood.api.model.mapper.RestauranteResumidoMapper;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    final RestauranteService service;
    final RestauranteMapper restauranteMapper;
    final RestauranteResumidoMapper restauranteResumidoMapper;

    public RestauranteController(RestauranteService service, RestauranteMapper restauranteMapper,
                                 RestauranteResumidoMapper restauranteResumidoMapper) {
        this.service = service;
        this.restauranteMapper = restauranteMapper;
        this.restauranteResumidoMapper = restauranteResumidoMapper;
    }

    @Override
    @GetMapping
    public CollectionModel<RestauranteResumidoResponse> listar() {
        List<Restaurante> restaurantes = service.listar();
        return restauranteResumidoMapper.toCollectionModel(restaurantes);
    }

    @Override
    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteResumidoResponse> listarSomenteNomes() {
        return listar();
    }

    @Override
    @GetMapping("/{restauranteId}")
    public RestauranteResponse buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscarOuFalhar(restauranteId);
        return restauranteMapper.toModel(restaurante);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponse salvar(@Valid @RequestBody RestauranteRequest request) {
        try {
            Restaurante restaurante = service.salvar(restauranteMapper.toDomain(request));
            return restauranteMapper.toModel(restaurante);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    @PutMapping("/{restauranteId}")
    public RestauranteResponse atualizar(@PathVariable Long restauranteId,
                                         @Valid @RequestBody RestauranteRequest request) {
        try {
            Restaurante restauranteSalvo = service.buscarOuFalhar(restauranteId);
            restauranteSalvo = restauranteMapper.toDomainCopy(restauranteSalvo, request);
            restauranteSalvo = service.salvar(restauranteSalvo);
            return restauranteMapper.toModel(restauranteSalvo);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        service.ativar(restauranteId);
    }

    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        service.inativar(restauranteId);
    }

    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        service.abrir(restauranteId);
    }

    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        service.fechar(restauranteId);
    }
}