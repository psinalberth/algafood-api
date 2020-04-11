package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoService {

    final FormaPagamentoRepository repository;

    public FormaPagamentoService(FormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public List<FormaPagamento> listar() {
        return repository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            repository.deleteById(formaPagamentoId);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        }
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        return repository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }
}