package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizacaoPedidoService {

    final CadastroPedidoService cadastroPedido;
    final ApplicationEventPublisher eventPublisher;

    public AtualizacaoPedidoService(CadastroPedidoService cadastroPedido, ApplicationEventPublisher eventPublisher) {
        this.cadastroPedido = cadastroPedido;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        eventPublisher.publishEvent(new PedidoConfirmadoEvent(pedido));
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
        pedido.cancelar();

        eventPublisher.publishEvent(new PedidoCanceladoEvent(pedido));
    }
}