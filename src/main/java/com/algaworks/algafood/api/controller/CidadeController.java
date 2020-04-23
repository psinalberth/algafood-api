package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.CidadeMapper;
import com.algaworks.algafood.api.model.request.CidadeRequest;
import com.algaworks.algafood.api.model.response.CidadeResponse;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
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
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    final CidadeService service;
    final CidadeMapper mapper;

    public CidadeController(CidadeService service, CidadeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public Page<CidadeResponse> listar(@PageableDefault(size = 20) Pageable pageable) {
        Page<Cidade> cidadesPage = service.listar(pageable);
        List<CidadeResponse> cidades = mapper.toCollectionResponse(cidadesPage.getContent());
        return new PageImpl<>(cidades, pageable, cidadesPage.getTotalElements());
    }

    @Override
    @GetMapping("/{cidadeId}")
    public CidadeResponse buscar(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscarOuFalhar(cidadeId);
        return mapper.toResponse(cidade);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse salvar(@Valid @RequestBody CidadeRequest request) {
        try {
            Cidade cidade = service.salvar(mapper.toModel(request));
            return mapper.toResponse(cidade);
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    @PutMapping("/{cidadeId}")
    public CidadeResponse atualizar(@PathVariable Long cidadeId,
                                                    @Valid @RequestBody CidadeRequest request) {
        try {
            Cidade cidadeSalva = service.buscarOuFalhar(cidadeId);
            cidadeSalva = mapper.toModelCopy(cidadeSalva, request);
            cidadeSalva = service.salvar(cidadeSalva);
            return mapper.toResponse(cidadeSalva);
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        service.excluir(cidadeId);
    }
}