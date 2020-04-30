package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.CozinhaRequest;
import com.algaworks.algafood.api.model.response.CozinhaResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    PagedModel<CozinhaResponse> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiProblem.class)
    })
    CozinhaResponse buscar(
            @ApiParam(value = "ID da cozinha", example = "1", required = true) Long cozinhaId
    );

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada")
    })
    CozinhaResponse salvar(
            @ApiParam(name = "corpo", value = "Representação de uma cozinha", required = true) CozinhaRequest request
    );

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiProblem.class)
    })
    CozinhaResponse atualizar(
            @ApiParam(value = "ID da cozinha", example = "1", required = true) Long cozinhaId,
            @ApiParam(name = "corpo", value = "Representação de uma cozinha", required = true) CozinhaRequest request
    );

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = ApiProblem.class)
    })
    void remover(
            @ApiParam(value = "ID da cozinha", example = "1", required = true) Long cozinhaId
    );
}