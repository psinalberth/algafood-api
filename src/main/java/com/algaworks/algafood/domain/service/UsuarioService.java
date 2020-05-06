package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    final UsuarioRepository repository;
    final GrupoService grupoService;
    final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, GrupoService grupoService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.grupoService = grupoService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {

        Optional<Usuario> usuarioSalvo = repository.findByEmail(usuario.getEmail());

        usuarioSalvo.ifPresent(u -> {
            if (!u.equals(usuario))
                throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s",
                        usuario.getEmail()));
        });

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

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
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada incorreta para o usuário selecionado.");
        }
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }
}