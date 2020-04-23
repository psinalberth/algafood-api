package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.CozinhaMapper;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    final CozinhaService service;
    final CozinhaMapper mapper;

    public CozinhaController(CozinhaService service, CozinhaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public Page<CozinhaResponse> listar(@PageableDefault(size = 20) Pageable pageable) {
        Page<Cozinha> cozinhasPage = service.listar(pageable);
        List<CozinhaResponse> cozinhas = mapper.toCollectionResponse(cozinhasPage.getContent());
        return new PageImpl<>(cozinhas, pageable, cozinhasPage.getTotalElements());
    }

    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaResponse buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = service.buscarOuFalhar(cozinhaId);
        return mapper.toResponse(cozinha);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponse salvar(@Valid @RequestBody CozinhaRequest request) {
        Cozinha cozinha = service.salvar(mapper.toModel(request));
        return mapper.toResponse(cozinha);
    }

    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaResponse atualizar(@PathVariable Long cozinhaId,
                                     @Valid @RequestBody CozinhaRequest request) {
        Cozinha cozinhaSalva = service.buscarOuFalhar(cozinhaId);
        cozinhaSalva = mapper.toModelCopy(cozinhaSalva, request);
        cozinhaSalva = service.salvar(cozinhaSalva);
        return mapper.toResponse(cozinhaSalva);
    }

    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        service.excluir(cozinhaId);
    }
}