package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.EstadoRequest;
import com.algaworks.algafood.api.model.response.EstadoResponse;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    List<EstadoResponse> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = ApiProblem.class)
    })
    EstadoResponse buscar(
            @ApiParam(value = "ID do estado", example = "1", required = true) Long estadoId
    );

    EstadoResponse salvar(
            @ApiParam(name = "corpo", value = "Representação da um estado", required = true) EstadoRequest request
    );

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = ApiProblem.class)
    })
    EstadoResponse atualizar(
            @ApiParam(value = "ID do estado", example = "1", required = true) Long estadoId,
            @ApiParam(name = "corpo", value = "Representação de um estado", required = true) EstadoRequest request
    );

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = ApiProblem.class)
    })
    void remover(Long estadoId);
}