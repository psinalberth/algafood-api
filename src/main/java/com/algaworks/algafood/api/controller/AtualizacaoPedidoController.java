package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.AtualizacaoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.AtualizacaoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        service.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        service.entregar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        service.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }
}