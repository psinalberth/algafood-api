package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.FotoProdutoMapper;
import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafood.domain.service.ProdutoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

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

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponse atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                         @Valid FotoProdutoRequest request,
                                         @RequestPart MultipartFile arquivo) throws IOException {
        FotoProduto fotoProduto = mapper.toModel(request);
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        fotoProduto.setProduto(produto);
        fotoProduto = service.salvar(fotoProduto, request.getArquivo().getInputStream());
        return mapper.toResponse(fotoProduto);
    }

    @Override
    @GetMapping
    public FotoProdutoResponse buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = service.buscarOuFalhar(restauranteId, produtoId);
        return mapper.toResponse(fotoProduto);
    }

    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> exibir(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                    @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {

            FotoProduto fotoProduto = service.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesPermitidas = MediaType.parseMediaTypes(acceptHeader);

            validarMediaTypeFoto(mediaTypeFoto, mediaTypesPermitidas);

            FotoRecuperada fotoRecuperada = storageService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temInputStream()) {

                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        service.excluir(restauranteId, produtoId);
    }

    private void validarMediaTypeFoto(MediaType mediaType, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean mediaTypePermitida = mediaTypesAceitas.stream()
                .anyMatch(mediaType::isCompatibleWith);

        if (!mediaTypePermitida) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}