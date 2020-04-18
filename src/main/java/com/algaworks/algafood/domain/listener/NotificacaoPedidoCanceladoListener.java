package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoPedidoCanceladoListener {

    final EnvioEmailService emailService;

    public NotificacaoPedidoCanceladoListener(EnvioEmailService emailService) {
        this.emailService = emailService;
    }

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("pedido-cancelado")
                .destinatario(pedido.getCliente().getEmail())
                .propriedade("pedido", pedido)
                .build();

        emailService.enviar(mensagem);
    }
}