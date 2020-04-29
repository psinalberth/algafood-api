package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.RestauranteRequest;
import com.algaworks.algafood.api.model.response.RestauranteResponse;
import com.algaworks.algafood.api.model.response.RestauranteResumidoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de restaurantes",
                              name = "projecao", paramType = "query", type = "string",
                              allowableValues = "apenas-nome")
    })
    CollectionModel<RestauranteResumidoResponse> listar();
    
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteResumidoResponse> listarSomenteNomes();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    RestauranteResponse buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId
    );

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado")
    })
    RestauranteResponse salvar(
            @ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
            RestauranteRequest request
    );

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    RestauranteResponse atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)  Long restauranteId,
            @ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
                    RestauranteRequest request
    );

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    ResponseEntity<Void> ativar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId
    );

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    ResponseEntity<Void> inativar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId
    );

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    ResponseEntity<Void> abrir(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId
    );

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    ResponseEntity<Void> fechar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId
    );
}