package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.GrupoMapper;
import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    final GrupoService service;
    final GrupoMapper mapper;

    public GrupoController(GrupoService service, GrupoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<GrupoResponse> listar() {
        List<Grupo> grupos = service.listar();
        return mapper.toCollectionResponse(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoResponse buscar(@PathVariable Long grupoId) {
        Grupo grupo = service.buscarOuFalhar(grupoId);
        return mapper.toResponse(grupo);
    }

    @PostMapping
    public ResponseEntity<GrupoResponse> salvar(@Valid @RequestBody GrupoRequest request) {
        Grupo grupo = service.salvar(mapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(grupo));
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoResponse> atualizar(@PathVariable Long grupoId,
                                                   @Valid @RequestBody GrupoRequest request) {
        Grupo grupo = service.buscarOuFalhar(grupoId);
        grupo = mapper.toModelCopy(grupo, request);
        grupo = service.salvar(grupo);
        return ResponseEntity.ok(mapper.toResponse(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        service.excluir(grupoId);
    }
}