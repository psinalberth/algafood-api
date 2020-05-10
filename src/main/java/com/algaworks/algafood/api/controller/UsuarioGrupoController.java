package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.GrupoMapper;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.algaworks.algafood.api.AlgaLinks.*;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    final UsuarioService usuarioService;
    final GrupoMapper grupoMapper;
    final AlgafoodSecurity algafoodSecurity;

    public UsuarioGrupoController(UsuarioService usuarioService, GrupoMapper grupoMapper, AlgafoodSecurity algafoodSecurity) {
        this.usuarioService = usuarioService;
        this.grupoMapper = grupoMapper;
        this.algafoodSecurity = algafoodSecurity;
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<GrupoResponse> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        CollectionModel<GrupoResponse> gruposResponse = grupoMapper.toCollectionModel(usuario.getGrupos())
                .removeLinks();

        if (algafoodSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposResponse.getContent().forEach(grupo -> {
                grupo.add(linkToUsuarioGrupoDesassociacao(usuarioId, grupo.getId(), "desassociar"));
            });

            gruposResponse.add(linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
        }

        gruposResponse.add(linkToUsuarioGrupos(usuarioId));

        return gruposResponse;
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeEditar
    @Override
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        try {
            usuarioService.associarGrupo(usuarioId, grupoId);
            return ResponseEntity.noContent().build();
        } catch (GrupoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @SecurityConstants.GruposUsuariosPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        try {
            usuarioService.desassociarGrupo(usuarioId, grupoId);
            return ResponseEntity.noContent().build();
        } catch (GrupoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}