package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.GrupoMapper;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    final UsuarioService usuarioService;
    final GrupoMapper grupoMapper;

    public UsuarioGrupoController(UsuarioService usuarioService, GrupoMapper grupoMapper) {
        this.usuarioService = usuarioService;
        this.grupoMapper = grupoMapper;
    }

    @GetMapping
    public List<GrupoResponse> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        return grupoMapper.toCollectionResponse(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        try {
            usuarioService.associarGrupo(usuarioId, grupoId);
        } catch (GrupoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        try {
            usuarioService.desassociarGrupo(usuarioId, grupoId);
        } catch (GrupoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}