package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.response.PermissaoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = ApiProblem.class)
    })
    CollectionModel<PermissaoResponse> listar(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId
    );

    @ApiOperation("Associa uma permissão a um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada",
                    response = ApiProblem.class)
    })
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId
    );

    @ApiOperation("Desassocia uma permissão de um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada",
                    response = ApiProblem.class)
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId
    );
}