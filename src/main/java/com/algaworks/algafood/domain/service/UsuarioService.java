package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    final UsuarioRepository repository;
    final GrupoService grupoService;

    public UsuarioService(UsuarioRepository repository, GrupoService grupoService) {
        this.repository = repository;
        this.grupoService = grupoService;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        usuario.removerGrupo(grupo);
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return repository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new NegocioException("Senha atual informada incorreta para o usuário selecionado.");
        }
        usuario.setSenha(novaSenha);
    }
}