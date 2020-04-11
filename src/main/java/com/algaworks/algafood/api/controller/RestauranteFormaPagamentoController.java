package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FormaPagamentoMapper;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    final RestauranteService restauranteService;
    final FormaPagamentoMapper formaPagamentoMapper;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService,
                                               FormaPagamentoMapper formaPagamentoMapper) {
        this.restauranteService = restauranteService;
        this.formaPagamentoMapper = formaPagamentoMapper;
    }

    @GetMapping
    public List<FormaPagamentoResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return formaPagamentoMapper.toCollectionResponse(new ArrayList<>(restaurante.getFormasPagamento()));
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        try {
            restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
        } catch (FormaPagamentoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        try {
            restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
        } catch (FormaPagamentoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}