package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CidadeService {

    final CidadeRepository repository;
    final EstadoService estadoService;

    public CidadeService(CidadeRepository repository, EstadoService estadoService) {
        this.repository = repository;
        this.estadoService = estadoService;
    }

    public List<Cidade> listar() {
        return repository.findAll();
    }

    public Page<Cidade> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);

        return repository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeId) {
        try {
            repository.deleteById(cidadeId);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new CidadeNaoEncontradaException(cidadeId);
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return repository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }
}