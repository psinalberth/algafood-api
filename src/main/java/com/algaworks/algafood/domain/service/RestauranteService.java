package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    final RestauranteRepository repository;
    final CozinhaService cozinhaService;
    final CidadeService cidadeService;
    final FormaPagamentoService formaPagamentoService;
    final UsuarioService usuarioService;

    public RestauranteService(RestauranteRepository repository, CozinhaService cozinhaService,
                              CidadeService cidadeService, FormaPagamentoService formaPagamentoService,
                              UsuarioService usuarioService) {
        this.repository = repository;
        this.cozinhaService = cozinhaService;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
        this.usuarioService = usuarioService;
    }

    public Page<Restaurante> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Restaurante> listar() {
        return repository.findAll();
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        Long cozinhaId = restaurante.getCozinha().getId();

        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.getEndereco().setCidade(cidade);
        restaurante.setCozinha(cozinha);

        return repository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.inativar();
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.fechar();
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario responsavel = usuarioService.buscarOuFalhar(usuarioId);
        restaurante.adicionarResponsavel(responsavel);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario responsavel = usuarioService.buscarOuFalhar(usuarioId);
        restaurante.removerResponsavel(responsavel);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return repository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}