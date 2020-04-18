package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.algaworks.algafood.domain.service.EnvioEmailService.*;

@Service
public class AtualizacaoPedidoService {

    final CadastroPedidoService cadastroPedido;
    final EnvioEmailService emailService;
    final ApplicationEventPublisher eventPublisher;

    public AtualizacaoPedidoService(CadastroPedidoService cadastroPedido, EnvioEmailService emailService, ApplicationEventPublisher eventPublisher) {
        this.cadastroPedido = cadastroPedido;
        this.emailService = emailService;
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