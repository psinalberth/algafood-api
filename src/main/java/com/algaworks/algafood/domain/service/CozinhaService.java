package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaService {

    private CozinhaRepository repository;

    public CozinhaService(CozinhaRepository repository) {
        this.repository = repository;
    }

    public Page<Cozinha> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return repository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId) {
        try {
            repository.deleteById(cozinhaId);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(cozinhaId);
        }
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return repository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }
}