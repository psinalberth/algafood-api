package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.GrupoRequest;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    CollectionModel<GrupoResponse> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiProblem.class)
    })
    GrupoResponse buscar(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId
    );

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoResponse salvar(
            @ApiParam(name = "corpo", value = "Representação do grupo", required = true) GrupoRequest request
    );

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiProblem.class)
    })
    GrupoResponse atualizar(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
            @ApiParam(name = "corpo", value = "Representação do grupo", required = true) GrupoRequest request
    );

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiProblem.class)
    })
    void remover(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId
    );
}