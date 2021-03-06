package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoPedidoConfirmadoListener {

    final EnvioEmailService emailService;

    public NotificacaoPedidoConfirmadoListener(EnvioEmailService emailService) {
        this.emailService = emailService;
    }

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado")
                .destinatario(pedido.getCliente().getEmail())
                .propriedade("pedido", pedido)
                .build();

        emailService.enviar(mensagem);
    }
}