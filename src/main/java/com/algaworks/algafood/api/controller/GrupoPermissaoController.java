package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.PermissaoMapper;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.algaworks.algafood.api.AlgaLinks.*;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    final GrupoService grupoService;
    final PermissaoMapper permissaoMapper;
    final AlgafoodSecurity algafoodSecurity;

    public GrupoPermissaoController(GrupoService grupoService, PermissaoMapper permissaoMapper,
                                    AlgafoodSecurity algafoodSecurity) {
        this.grupoService = grupoService;
        this.permissaoMapper = permissaoMapper;
        this.algafoodSecurity = algafoodSecurity;
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<PermissaoResponse> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        CollectionModel<PermissaoResponse> permissoes = permissaoMapper.toCollectionModel(grupo.getPermissoes())
                .removeLinks();

        if (algafoodSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoes.getContent().forEach(permissao -> {
                permissao.add(linkToGrupoPermissaoDesassociacao(grupoId, permissao.getId(), "desassociar"));
            });

            permissoes.add(linkToGrupoPermissaoAssociacao(grupoId, "associar"));
        }

        permissoes.add(linkToGrupoPermissoes(grupoId));

        return permissoes;
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeEditar
    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        try {
            grupoService.associarPermissao(grupoId, permissaoId);
            return ResponseEntity.noContent().build();
        } catch (PermissaoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        try {
            grupoService.desassociarPermissao(grupoId, permissaoId);
            return ResponseEntity.noContent().build();
        } catch (PermissaoNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}