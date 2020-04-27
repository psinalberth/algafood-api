package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.EstadoMapper;
import com.algaworks.algafood.api.model.request.EstadoRequest;
import com.algaworks.algafood.api.model.response.EstadoResponse;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    final EstadoService service;
    final EstadoMapper mapper;

    public EstadoController(EstadoService service, EstadoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    public CollectionModel<EstadoResponse> listar() {
        List<Estado> estados = service.listar();
        return mapper.toCollectionModel(estados);
    }

    @Override
    @GetMapping("/{estadoId}")
    public EstadoResponse buscar(@PathVariable Long estadoId) {
        Estado estado = service.buscarOuFalhar(estadoId);
        return mapper.toModel(estado);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoResponse salvar(@Valid @RequestBody EstadoRequest request) {
        Estado estado = service.salvar(mapper.toDomain(request));
        return mapper.toModel(estado);
    }

    @Override
    @PutMapping("/{estadoId}")
    public EstadoResponse atualizar(@PathVariable Long estadoId,
                                    @Valid @RequestBody EstadoRequest request) {
        Estado estadoSalvo = service.buscarOuFalhar(estadoId);
        estadoSalvo = mapper.toDomainCopy(estadoSalvo, request);
        estadoSalvo = service.salvar(estadoSalvo);
        return mapper.toModel(estadoSalvo);
    }

    @Override
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        service.excluir(estadoId);
    }
}