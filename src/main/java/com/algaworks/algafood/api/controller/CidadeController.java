package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.CidadeMapper;
import com.algaworks.algafood.api.model.request.CidadeRequest;
import com.algaworks.algafood.api.model.response.CidadeResponse;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
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
@RequestMapping("/cidades")
public class CidadeController {

    final CidadeService service;
    final CidadeMapper mapper;

    public CidadeController(CidadeService service, CidadeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<CidadeResponse> listar(@PageableDefault(size = 20) Pageable pageable) {
        Page<Cidade> cidadesPage = service.listar(pageable);
        List<CidadeResponse> cidades = mapper.toCollectionResponse(cidadesPage.getContent());
        return new PageImpl<CidadeResponse>(cidades, pageable, cidadesPage.getTotalElements());
    }

    @GetMapping("/{cidadeId}")
    public CidadeResponse buscar(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscarOuFalhar(cidadeId);
        return mapper.toResponse(cidade);
    }

    @PostMapping
    public ResponseEntity<CidadeResponse> salvar(@Valid @RequestBody CidadeRequest request) {
        try {
            Cidade cidade = service.salvar(mapper.toModel(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cidade));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeResponse> atualizar(@PathVariable Long cidadeId,
                                                    @Valid @RequestBody CidadeRequest request) {
        try {
            Cidade cidadeSalva = service.buscarOuFalhar(cidadeId);
            cidadeSalva = mapper.toModelCopy(cidadeSalva, request);
            cidadeSalva = service.salvar(cidadeSalva);
            return ResponseEntity.ok(mapper.toResponse(cidadeSalva));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        service.excluir(cidadeId);
    }
}