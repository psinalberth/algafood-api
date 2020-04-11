package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.AtualizacaoPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class AtualizacaoPedidoController {

    final AtualizacaoPedidoService service;

    public AtualizacaoPedidoController(AtualizacaoPedidoService service) {
        this.service = service;
    }

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        service.confirmar(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        service.entregar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        service.cancelar(codigoPedido);
    }
}