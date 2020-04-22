package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.CidadeRequest;
import com.algaworks.algafood.api.model.response.CidadeResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades com paginação")
    Page<CidadeResponse> listar(Pageable pageable);

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiProblem.class)
    })
    CidadeResponse buscar(
            @ApiParam(value = "ID da cidade", example = "1", required = true) Long cidadeId
    );

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    CidadeResponse salvar(
            @ApiParam(name = "corpo", value = "Representação da uma cidade", required = true) CidadeRequest request
    );

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiProblem.class)
    })
    CidadeResponse atualizar(
            @ApiParam(value = "ID da cidade", example = "1", required = true) Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma cidade", required = true) CidadeRequest request);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = ApiProblem.class)
    })
    void remover(
            @ApiParam(value = "ID da cidade", example = "1", required = true) Long cidadeId
    );
}