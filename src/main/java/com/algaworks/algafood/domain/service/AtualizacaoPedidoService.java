package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.algaworks.algafood.domain.service.EnvioEmailService.*;

@Service
public class AtualizacaoPedidoService {

    final CadastroPedidoService cadastroPedido;
    final EnvioEmailService emailService;

    public AtualizacaoPedidoService(CadastroPedidoService cadastroPedido, EnvioEmailService emailService) {
        this.cadastroPedido = cadastroPedido;
        this.emailService = emailService;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado")
                .destinatario(pedido.getCliente().getEmail())
                .propriedade("pedido", pedido)
                .build();

        emailService.enviar(mensagem);
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
    }
}