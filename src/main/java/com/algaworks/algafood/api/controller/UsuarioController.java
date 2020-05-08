package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.UsuarioMapper;
import com.algaworks.algafood.api.model.request.AlteracaoSenhaRequest;
import com.algaworks.algafood.api.model.request.UsuarioRequest;
import com.algaworks.algafood.api.model.request.UsuarioSenhaRequest;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    final UsuarioService service;
    final UsuarioMapper mapper;

    public UsuarioController(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<UsuarioResponse> listar() {
        List<Usuario> usuarios = service.listar();
        return mapper.toCollectionModel(usuarios);
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeConsultar
    @Override
    @GetMapping("/{usuarioId}")
    public UsuarioResponse buscar(@PathVariable Long usuarioId) {
        Usuario usuario = service.buscarOuFalhar(usuarioId);
        return mapper.toModel(usuario);
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse salvar(@Valid @RequestBody UsuarioSenhaRequest request) {
        Usuario usuario = service.salvar(mapper.toDomain(request));
        return mapper.toModel(usuario);
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeAlterarUsuario
    @Override
    @PutMapping("/{usuarioId}")
    public UsuarioResponse atualizar(@PathVariable Long usuarioId,
                                     @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = service.buscarOuFalhar(usuarioId);
        usuario = mapper.toDomainCopy(usuario, request);
        usuario = service.salvar(usuario);
        return mapper.toModel(usuario);
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeAlterarPropriaSenha
    @Override
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @Valid @RequestBody AlteracaoSenhaRequest request) {
        service.alterarSenha(usuarioId, request.getSenhaAtual(), request.getNovaSenha());
    }
}