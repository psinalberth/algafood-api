package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.GrupoMapper;
import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    final GrupoService service;
    final GrupoMapper mapper;

    public GrupoController(GrupoService service, GrupoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public CollectionModel<GrupoResponse> listar() {
        List<Grupo> grupos = service.listar();
        return mapper.toCollectionModel(grupos);
    }

    @Override
    @GetMapping("/{grupoId}")
    public GrupoResponse buscar(@PathVariable Long grupoId) {
        Grupo grupo = service.buscarOuFalhar(grupoId);
        return mapper.toModel(grupo);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoResponse salvar(@Valid @RequestBody GrupoRequest request) {
        Grupo grupo = service.salvar(mapper.toDomain(request));
        return mapper.toModel(grupo);
    }

    @Override
    @PutMapping("/{grupoId}")
    public GrupoResponse atualizar(@PathVariable Long grupoId,
                                                   @Valid @RequestBody GrupoRequest request) {
        Grupo grupo = service.buscarOuFalhar(grupoId);
        grupo = mapper.toDomainCopy(grupo, request);
        grupo = service.salvar(grupo);
        return mapper.toModel(grupo);
    }

    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        service.excluir(grupoId);
    }
}