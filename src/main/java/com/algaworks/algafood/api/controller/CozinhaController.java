package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.CozinhaMapper;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    final CozinhaService service;
    final CozinhaMapper mapper;

    public CozinhaController(CozinhaService service, CozinhaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<CozinhaResponse> listar(@PageableDefault(size = 20) Pageable pageable) {
        Page<Cozinha> cozinhasPage = service.listar(pageable);
        List<CozinhaResponse> cozinhas = mapper.toCollectionResponse(cozinhasPage.getContent());
        return new PageImpl<CozinhaResponse>(cozinhas, pageable, cozinhasPage.getTotalElements());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaResponse buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = service.buscarOuFalhar(cozinhaId);
        return mapper.toResponse(cozinha);
    }

    @PostMapping
    public ResponseEntity<CozinhaResponse> salvar(@Valid @RequestBody CozinhaRequest request) {
        Cozinha cozinha = service.salvar(mapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaResponse> atualizar(@PathVariable Long cozinhaId,
                                                     @Valid @RequestBody CozinhaRequest request) {
        Cozinha cozinhaSalva = service.buscarOuFalhar(cozinhaId);
        cozinhaSalva = mapper.toModelCopy(cozinhaSalva, request);
        cozinhaSalva = service.salvar(cozinhaSalva);
        return ResponseEntity.ok(mapper.toResponse(cozinhaSalva));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        service.excluir(cozinhaId);
    }
}