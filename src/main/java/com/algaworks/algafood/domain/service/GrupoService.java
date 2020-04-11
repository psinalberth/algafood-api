package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    final GrupoRepository repository;
    final PermissaoService permissaoService;

    public GrupoService(GrupoRepository repository, PermissaoService permissaoService) {
        this.repository = repository;
        this.permissaoService = permissaoService;
    }

    public List<Grupo> listar() {
        return repository.findAll();
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return repository.save(grupo);
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);
    }

    @Transactional
    public void excluir(Long grupoId) {
        try {
            repository.deleteById(grupoId);
            repository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new GrupoNaoEncontradoException(grupoId);
        }
    }

    public Grupo buscarOuFalhar(Long grupoId) {
        return repository.findById(grupoId).orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }
}