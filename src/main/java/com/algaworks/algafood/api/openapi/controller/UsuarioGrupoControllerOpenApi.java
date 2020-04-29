package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.response.GrupoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiProblem.class)
    })
    CollectionModel<GrupoResponse> listar(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId
    );

    @ApiOperation("Associa um grupo a um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = ApiProblem.class)
    })
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId
    );

    @ApiOperation("Desassocia um grupo de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = ApiProblem.class)
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId
    );
}