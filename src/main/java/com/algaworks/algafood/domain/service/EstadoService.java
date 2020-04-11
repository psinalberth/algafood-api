package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoService {

    final EstadoRepository repository;

    public EstadoService(EstadoRepository repository) {
        this.repository = repository;
    }

    public List<Estado> listar() {
        return repository.findAll();
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }

    @Transactional
    public void excluir(Long estadoId) {
        try {
            repository.deleteById(estadoId);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new EstadoNaoEncontradoException(estadoId);
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return repository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}