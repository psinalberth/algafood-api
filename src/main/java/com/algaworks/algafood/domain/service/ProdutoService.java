package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    final ProdutoRepository repository;
    final RestauranteService restauranteService;

    public ProdutoService(ProdutoRepository repository, RestauranteService restauranteService) {
        this.repository = repository;
        this.restauranteService = restauranteService;
    }

    public List<Produto> listarTodos(Restaurante restaurante, boolean incluirtInativos) {
        if (incluirtInativos)
            return repository.findAllByRestaurante(restaurante);
        return repository.findAllAtivosByRestaurante(restaurante);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        Long restauranteId = produto.getRestaurante().getId();
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        produto.setRestaurante(restaurante);
        return repository.save(produto);
    }

    @Transactional
    public void ativar(Long restauranteId, Long produtoId) {
        Produto produto = buscarOuFalhar(restauranteId, produtoId);
        produto.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId, Long produtoId) {
        Produto produto = buscarOuFalhar(restauranteId, produtoId);
        produto.inativar();
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }
}