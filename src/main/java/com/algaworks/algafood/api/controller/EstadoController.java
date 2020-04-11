package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.EstadoMapper;
import com.algaworks.algafood.api.model.request.EstadoRequest;
import com.algaworks.algafood.api.model.response.EstadoResponse;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    final EstadoService service;
    final EstadoMapper mapper;

    public EstadoController(EstadoService service, EstadoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<EstadoResponse> listar() {
        List<Estado> estados = service.listar();
        return mapper.toCollectionResponse(estados);
    }

    @GetMapping("/{estadoId}")
    public EstadoResponse buscar(@PathVariable Long estadoId) {
        Estado estado = service.buscarOuFalhar(estadoId);
        return mapper.toResponse(estado);
    }

    @PostMapping
    public ResponseEntity<EstadoResponse> salvar(@Valid @RequestBody EstadoRequest request) {
        Estado estado = service.salvar(mapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(estado));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoResponse> atualizar(@PathVariable Long estadoId,
                                                    @Valid @RequestBody EstadoRequest request) {
        Estado estadoSalvo = service.buscarOuFalhar(estadoId);
        estadoSalvo = mapper.toModelCopy(estadoSalvo, request);
        estadoSalvo = service.salvar(estadoSalvo);
        return ResponseEntity.ok(mapper.toResponse(estadoSalvo));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        service.excluir(estadoId);
    }
}