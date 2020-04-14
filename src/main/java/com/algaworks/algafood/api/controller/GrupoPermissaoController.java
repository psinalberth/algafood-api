package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.PermissaoMapper;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    final GrupoService grupoService;
    final PermissaoMapper permissaoMapper;

    public GrupoPermissaoController(GrupoService grupoService, PermissaoMapper permissaoMapper) {
        this.grupoService = grupoService;
        this.permissaoMapper = permissaoMapper;
    }

    @GetMapping
    public List<PermissaoResponse> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return permissaoMapper.toCollectionResponse(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        try {
            grupoService.associarPermissao(grupoId, permissaoId);
        } catch (PermissaoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        try {
            grupoService.desassociarPermissao(grupoId, permissaoId);
        } catch (PermissaoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}