package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizacaoPedidoService {

    final CadastroPedidoService cadastroPedido;

    public AtualizacaoPedidoService(CadastroPedidoService cadastroPedido) {
        this.cadastroPedido = cadastroPedido;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
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