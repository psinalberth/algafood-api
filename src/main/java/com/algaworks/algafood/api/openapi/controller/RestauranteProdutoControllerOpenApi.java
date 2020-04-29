package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.ProdutoRequest;
import com.algaworks.algafood.api.model.response.ProdutoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation(value = "Lista os produtos de um restaurantes")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    CollectionModel<ProdutoResponse> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "Indica se deve ou não incluir produtos inativos na lista", example = "false",
                      defaultValue = "false")
                    Boolean incluirInativos
    );

    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiProblem.class)
    })
    ProdutoResponse buscar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
    );

    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = ApiProblem.class)
    })
    ProdutoResponse salvar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(name = "corpo", value = "Representação de um produto", required = true) ProdutoRequest request
    );

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiProblem.class)
    })
    ProdutoResponse atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
            @ApiParam(name = "corpo", value = "Representação de um produto", required = true) ProdutoRequest request
    );

    @ApiOperation("Ativa um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto ativado com sucesso"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiProblem.class)
    })
    void ativar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
    );

    @ApiOperation("Inativa um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto inativado com sucesso"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = ApiProblem.class)
    })
    void inativar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId
    );
}