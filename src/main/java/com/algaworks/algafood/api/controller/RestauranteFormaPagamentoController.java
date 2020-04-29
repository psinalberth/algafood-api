package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FormaPagamentoMapper;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    final RestauranteService restauranteService;
    final FormaPagamentoMapper formaPagamentoMapper;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService,
                                               FormaPagamentoMapper formaPagamentoMapper) {
        this.restauranteService = restauranteService;
        this.formaPagamentoMapper = formaPagamentoMapper;
    }

    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        CollectionModel<FormaPagamentoResponse> formasPagamentoResponse =
                formaPagamentoMapper.toCollectionModel(restaurante.getFormasPagamento());

        formasPagamentoResponse.getContent().forEach(formaPagamentoResponse -> {
            formaPagamentoResponse.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                    .desassociar(restauranteId, formaPagamentoResponse.getId())).withRel("desassociar"));
        });

        return formasPagamentoResponse.removeLinks()
                .add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                        .listar(restauranteId)).withSelfRel())
                .add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                        .associar(restauranteId, null)).withRel("associar"));
    }

    @Override
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        try {
            restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
            return ResponseEntity.noContent().build();
        } catch (FormaPagamentoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        try {
            restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
            return ResponseEntity.noContent().build();
        } catch (FormaPagamentoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}