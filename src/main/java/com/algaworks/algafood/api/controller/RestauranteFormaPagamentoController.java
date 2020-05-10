package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FormaPagamentoMapper;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.algaworks.algafood.api.AlgaLinks.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    final RestauranteService restauranteService;
    final FormaPagamentoMapper formaPagamentoMapper;
    final AlgafoodSecurity algafoodSecurity;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService,
                                               FormaPagamentoMapper formaPagamentoMapper,
                                               AlgafoodSecurity algafoodSecurity) {
        this.restauranteService = restauranteService;
        this.formaPagamentoMapper = formaPagamentoMapper;
        this.algafoodSecurity = algafoodSecurity;
    }

    @SecurityConstants.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        CollectionModel<FormaPagamentoResponse> formasPagamentoResponse =
                formaPagamentoMapper.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks();

        if (algafoodSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            formasPagamentoResponse.getContent().forEach(formaPagamento -> {
                formaPagamento.add(linkToRestauranteFormaPagamentoDesassociacao(
                        restauranteId, formaPagamento.getId(), "desassociar")
                );
            });

            formasPagamentoResponse.add(linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
        }

        formasPagamentoResponse.add(linkToRestauranteFormasPagamento(restauranteId));

        return formasPagamentoResponse;
    }

    @SecurityConstants.Restaurantes.PodeGerenciarFuncionamento
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

    @SecurityConstants.Restaurantes.PodeGerenciarFuncionamento
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