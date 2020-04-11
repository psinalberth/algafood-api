package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.UsuarioMapper;
import com.algaworks.algafood.api.model.request.AlteracaoSenhaRequest;
import com.algaworks.algafood.api.model.request.UsuarioRequest;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    final UsuarioService service;
    final UsuarioMapper mapper;

    public UsuarioController(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        List<Usuario> usuarios = service.listar();
        return mapper.toCollectionResponse(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioResponse buscar(@PathVariable Long usuarioId) {
        Usuario usuario = service.buscarOuFalhar(usuarioId);
        return mapper.toResponse(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> salvar(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = service.salvar(mapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(usuario));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long usuarioId,
                                                     @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = service.buscarOuFalhar(usuarioId);
        usuario = mapper.toModelCopy(usuario, request);
        usuario = service.salvar(usuario);
        return ResponseEntity.ok(mapper.toResponse(usuario));
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @Valid @RequestBody AlteracaoSenhaRequest request) {
        service.alterarSenha(usuarioId, request.getSenhaAtual(), request.getNovaSenha());
    }
}