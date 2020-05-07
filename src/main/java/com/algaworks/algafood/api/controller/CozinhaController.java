package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.CozinhaMapper;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    final CozinhaService service;
    final CozinhaMapper mapper;
    final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    public CozinhaController(CozinhaService service, CozinhaMapper mapper, PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.service = service;
        this.mapper = mapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @SecurityConstants.Cozinhas.PodeConsultar
    @Override
    @GetMapping
    public PagedModel<CozinhaResponse> listar(@PageableDefault(size = 20) Pageable pageable) {
        Page<Cozinha> cozinhasPage = service.listar(pageable);
        return pagedResourcesAssembler.toModel(cozinhasPage, mapper);
    }

    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponse buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = service.buscarOuFalhar(cozinhaId);
        return mapper.toModel(cozinha);
    }

    @SecurityConstants.Cozinhas.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponse salvar(@Valid @RequestBody CozinhaRequest request) {
        Cozinha cozinha = service.salvar(mapper.toDomain(request));
        return mapper.toModel(cozinha);
    }

    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponse atualizar(@PathVariable Long cozinhaId,
                                     @Valid @RequestBody CozinhaRequest request) {
        Cozinha cozinhaSalva = service.buscarOuFalhar(cozinhaId);
        cozinhaSalva = mapper.toDomainCopy(cozinhaSalva, request);
        cozinhaSalva = service.salvar(cozinhaSalva);
        return mapper.toModel(cozinhaSalva);
    }

    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        service.excluir(cozinhaId);
    }
}