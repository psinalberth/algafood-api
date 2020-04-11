package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.PermissaoMapper;
import com.algaworks.algafood.api.model.request.PermissaoRequest;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.PermissaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    final PermissaoService service;
    final PermissaoMapper mapper;

    public PermissaoController(PermissaoService service, PermissaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PermissaoResponse> listar() {
        List<Permissao> permissoes = service.listar();
        return mapper.toCollectionResponse(permissoes);
    }

    @GetMapping("/{permissaoId}")
    public PermissaoResponse buscar(@PathVariable Long permissaoId) {
        Permissao permissao = service.buscarOuFalhar(permissaoId);
        return mapper.toResponse(permissao);
    }

    @PostMapping
    public ResponseEntity<PermissaoResponse> salvar(@Valid @RequestBody PermissaoRequest request) {
        Permissao permissao = service.salvar(mapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(permissao));
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<PermissaoResponse> atualizar(@PathVariable Long permissaoId,
                                                       @Valid @RequestBody PermissaoRequest request) {
        Permissao permissaoSalva = service.buscarOuFalhar(permissaoId);
        permissaoSalva = mapper.toModelCopy(permissaoSalva, request);
        permissaoSalva = service.salvar(permissaoSalva);
        return ResponseEntity.ok(mapper.toResponse(permissaoSalva));
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long permissaoId) {
        service.excluir(permissaoId);
    }
}