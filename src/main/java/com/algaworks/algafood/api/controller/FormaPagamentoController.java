package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FormaPagamentoMapper;
import com.algaworks.algafood.api.model.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    final FormaPagamentoService service;
    final FormaPagamentoMapper mapper;

    public FormaPagamentoController(FormaPagamentoService service, FormaPagamentoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<FormaPagamentoResponse> listar() {
        List<FormaPagamento> formasPagamento = service.listar();
        return mapper.toCollectionResponse(formasPagamento);
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoResponse buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = service.buscarOuFalhar(formaPagamentoId);
        return mapper.toResponse(formaPagamento);
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoResponse> salvar(@Valid @RequestBody FormaPagamentoRequest request) {
        FormaPagamento formaPagamento = service.salvar(mapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(formaPagamento));
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoResponse> atualizar(@PathVariable Long formaPagamentoId,
                                                            @Valid @RequestBody FormaPagamentoRequest request) {
        FormaPagamento formaPagamentoSalva = service.buscarOuFalhar(formaPagamentoId);
        formaPagamentoSalva = mapper.toModelCopy(formaPagamentoSalva, request);
        formaPagamentoSalva = service.salvar(formaPagamentoSalva);
        return ResponseEntity.ok(mapper.toResponse(formaPagamentoSalva));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        service.excluir(formaPagamentoId);
    }
}