package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.PermissaoRequest;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoResponse> listar();

    @ApiOperation("Busca uma permissão por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da permissão inválida", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Permissão não encontrada", response = ApiProblem.class)
    })
    PermissaoResponse buscar(
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId
    );

    @ApiOperation("Cadastra uma permissão")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Permissão cadastrada"),
    })
    PermissaoResponse salvar(
            @ApiParam(name = "corpo", value = "Representação da permissão", required = true) PermissaoRequest request
    );

    @ApiOperation("Atualiza uma permissão por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Permissão atualizada"),
            @ApiResponse(code = 404, message = "Permissão não encontrada", response = ApiProblem.class)
    })
    PermissaoResponse atualizar(
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId,
            @ApiParam(name = "corpo", value = "Representação da um permissão", required = true) PermissaoRequest request
    );

    @ApiOperation("Exclui uma permissão por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão excluída"),
            @ApiResponse(code = 404, message = "Permissão não encontrada", response = ApiProblem.class)
    })
    void remover(
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId
    );
}
