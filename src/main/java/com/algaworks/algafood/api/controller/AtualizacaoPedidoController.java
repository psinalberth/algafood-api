package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.AtualizacaoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.AtualizacaoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class AtualizacaoPedidoController implements AtualizacaoPedidoControllerOpenApi {

    final AtualizacaoPedidoService service;

    public AtualizacaoPedidoController(AtualizacaoPedidoService service) {
        this.service = service;
    }

    @Override
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        service.confirmar(codigoPedido);
    }

    @Override
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        service.entregar(codigoPedido);
    }

    @Override
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        service.cancelar(codigoPedido);
    }
}