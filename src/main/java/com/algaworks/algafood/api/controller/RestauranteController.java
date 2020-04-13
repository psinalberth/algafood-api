package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.RestauranteMapper;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    final RestauranteService service;
    final RestauranteMapper mapper;

    public RestauranteController(RestauranteService service, RestauranteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<RestauranteResumidoResponse> listar() {
        List<Restaurante> restaurantes = service.listar();
        return mapper.toFilteredCollectionResponse(restaurantes);
    }

    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteResumidoResponse> listarSomenteNomes() {
        return listar();
    }

    @GetMapping("/{restauranteId}")
    public RestauranteResponse buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscarOuFalhar(restauranteId);
        return mapper.toResponse(restaurante);
    }

    @PostMapping
    public ResponseEntity<RestauranteResponse> salvar(@Valid @RequestBody RestauranteRequest request) {
        try {
            Restaurante restaurante = service.salvar(mapper.toModel(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(restaurante));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long restauranteId,
                                                         @Valid @RequestBody RestauranteRequest request) {
        try {
            Restaurante restauranteSalvo = service.buscarOuFalhar(restauranteId);
            restauranteSalvo = mapper.toModelCopy(restauranteSalvo, request);
            restauranteSalvo = service.salvar(restauranteSalvo);
            return ResponseEntity.ok(mapper.toResponse(restauranteSalvo));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        service.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        service.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        service.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        service.fechar(restauranteId);
    }
}