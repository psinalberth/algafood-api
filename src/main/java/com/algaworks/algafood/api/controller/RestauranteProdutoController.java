package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.ProdutoMapper;
import com.algaworks.algafood.api.model.request.ProdutoRequest;
import com.algaworks.algafood.api.model.response.ProdutoResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    final ProdutoService produtoService;
    final RestauranteService restauranteService;
    final ProdutoMapper produtoMapper;

    public RestauranteProdutoController(ProdutoService produtoService, RestauranteService restauranteService,
                                        ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.restauranteService = restauranteService;
        this.produtoMapper = produtoMapper;
    }

    @Override
    @GetMapping
    public List<ProdutoResponse> listar(@PathVariable Long restauranteId,
                                        @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos = produtoService.listarTodos(restaurante, incluirInativos);
        return produtoMapper.toCollectionResponse(produtos);
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoResponse buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        return produtoMapper.toResponse(produto);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse salvar(@PathVariable Long restauranteId,
                                  @Valid @RequestBody ProdutoRequest request) {
        Produto produto = produtoService.salvar(produtoMapper.toModel(restauranteId, request));
        return produtoMapper.toResponse(produto);
    }

    @Override
    @PutMapping("/{produtoId}")
    public ProdutoResponse atualizar(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId,
                                     @Valid @RequestBody ProdutoRequest request) {
        Produto produtoSalvo = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoSalvo = produtoMapper.toModellCopy(produtoSalvo, request);
        produtoSalvo = produtoService.salvar(produtoSalvo);
        return produtoMapper.toResponse(produtoSalvo);
    }

    @Override
    @PutMapping("/{produtoId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        produtoService.ativar(restauranteId, produtoId);
    }

    @Override
    @DeleteMapping("/{produtoId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        produtoService.inativar(restauranteId, produtoId);
    }
}