package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPedidoService {

    final PedidoRepository repository;
    final RestauranteService restauranteService;
    final CidadeService cidadeService;
    final ProdutoService produtoService;
    final FormaPagamentoService formaPagamentoService;
    final UsuarioService usuarioService;

    public CadastroPedidoService(PedidoRepository repository, RestauranteService restauranteService,
                                 CidadeService cidadeService, ProdutoService produtoService,
                                 FormaPagamentoService formaPagamentoService, UsuarioService usuarioService) {
        this.repository = repository;
        this.restauranteService = restauranteService;
        this.cidadeService = cidadeService;
        this.produtoService = produtoService;
        this.formaPagamentoService = formaPagamentoService;
        this.usuarioService = usuarioService;
    }

    public Page<Pedido> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Pedido> listar(PedidoFilter filtro, Pageable pageable) {
        return repository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItensPedido(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return repository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
    }

    private void validarItensPedido(Pedido pedido) {
        Long restauranteId = pedido.getRestaurante().getId();
        pedido.getItens().forEach(item -> {
            Long produtoId = item.getProduto().getId();
            Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    public Pedido buscarOuFalhar(String codigoPedido) {
        return repository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }
}