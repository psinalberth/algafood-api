package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.FotoProdutoRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiProblem.class)
    })
    FotoProdutoResponse atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
            FotoProdutoRequest request,
            @ApiParam(value = "Arquivo da foto do produto (PNG ou JPEG, no máximo 500KB)", required = true)
            MultipartFile arquivo) throws IOException;

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
                  produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = ApiProblem.class)
    })
    FotoProdutoResponse buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
    );

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> exibir(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto excluída"),
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = ApiProblem.class)
    })
    void excluir(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
    );
}
