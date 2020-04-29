package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoResponse>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = ApiProblem.class)
    })
    ResponseEntity<FormaPagamentoResponse> buscar(
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId,
                                                  ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada")
    })
    FormaPagamentoResponse salvar(
            @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento", required = true)
            FormaPagamentoRequest request
    );

    @ApiOperation("Atualiza uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = ApiProblem.class)
    })
    FormaPagamentoResponse atualizar(
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId,
            @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento", required = true)
                    FormaPagamentoRequest request);

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = ApiProblem.class)
    })
    void remover(
            @ApiParam(value = "ID da cozinha", example = "1", required = true) Long formaPagamentoId
    );
}