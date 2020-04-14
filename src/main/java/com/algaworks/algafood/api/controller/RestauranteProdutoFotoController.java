package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FotoProdutoMapper;
import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    final FotoProdutoService service;
    final ProdutoService produtoService;
    final FotoStorageService storageService;
    final FotoProdutoMapper mapper;

    public RestauranteProdutoFotoController(FotoProdutoService service, ProdutoService produtoService, FotoStorageService storageService, FotoProdutoMapper mapper) {
        this.service = service;
        this.produtoService = produtoService;
        this.storageService = storageService;
        this.mapper = mapper;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponse atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                         @Valid FotoProdutoRequest request) throws IOException {
        FotoProduto fotoProduto = mapper.toModel(request);
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        fotoProduto.setProduto(produto);
        fotoProduto = service.salvar(fotoProduto, request.getArquivo().getInputStream());
        return mapper.toResponse(fotoProduto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoResponse buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = service.buscarOuFalhar(restauranteId, produtoId);
        return mapper.toResponse(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<?> exibir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        try {

            FotoProduto fotoProduto = service.buscarOuFalhar(restauranteId, produtoId);
            InputStream inputStream = storageService.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}