package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FormaPagamentoMapper;
import com.algaworks.algafood.api.model.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.SecurityConstants;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    final FormaPagamentoService service;
    final FormaPagamentoRepository repository;
    final FormaPagamentoMapper mapper;

    public FormaPagamentoController(FormaPagamentoService service, FormaPagamentoRepository repository,
                                    FormaPagamentoMapper mapper) {
        this.service = service;
        this.repository = repository;
        this.mapper = mapper;
    }

    @SecurityConstants.FormasPagamento.PodeConsultar
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoResponse>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = repository.getUltimaDataAtualizacao();

        if (dataUltimaAtualizacao != null)
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());

        if (request.checkNotModified(eTag))
            return null;

        List<FormaPagamento> formasPagamento = service.listar();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(mapper.toCollectionModel(formasPagamento));
    }

    @SecurityConstants.FormasPagamento.PodeConsultar
    @Override
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoResponse> buscar(@PathVariable Long formaPagamentoId,
                                                         ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = repository.getDataAtualizacaoById(formaPagamentoId);

        if (dataUltimaAtualizacao != null)
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());

        if (request.checkNotModified(eTag))
            return null;

        FormaPagamento formaPagamento = service.buscarOuFalhar(formaPagamentoId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(mapper.toModel(formaPagamento));
    }

    @SecurityConstants.FormasPagamento.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoResponse salvar(@Valid @RequestBody FormaPagamentoRequest request) {
        FormaPagamento formaPagamento = service.salvar(mapper.toDomain(request));
        return mapper.toModel(formaPagamento);
    }

    @SecurityConstants.FormasPagamento.PodeEditar
    @Override
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoResponse atualizar(@PathVariable Long formaPagamentoId,
                                            @Valid @RequestBody FormaPagamentoRequest request) {
        FormaPagamento formaPagamentoSalva = service.buscarOuFalhar(formaPagamentoId);
        formaPagamentoSalva = mapper.toDomainCopy(formaPagamentoSalva, request);
        formaPagamentoSalva = service.salvar(formaPagamentoSalva);
        return mapper.toModel(formaPagamentoSalva);
    }

    @SecurityConstants.FormasPagamento.PodeEditar
    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        service.excluir(formaPagamentoId);
    }
}