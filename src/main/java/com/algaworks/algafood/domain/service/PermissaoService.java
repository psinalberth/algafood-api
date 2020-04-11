package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissaoService {

    final PermissaoRepository repository;

    public PermissaoService(PermissaoRepository repository) {
        this.repository = repository;
    }

    public List<Permissao> listar() {
        return repository.findAll();
    }

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return repository.save(permissao);
    }

    @Transactional
    public void excluir(Long permissaoId) {
        try {
            repository.deleteById(permissaoId);
            repository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new PermissaoNaoEncontradaException(permissaoId);
        }
    }

    public Permissao buscarOuFalhar(Long permissaoId) {
        return repository.findById(permissaoId).orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}