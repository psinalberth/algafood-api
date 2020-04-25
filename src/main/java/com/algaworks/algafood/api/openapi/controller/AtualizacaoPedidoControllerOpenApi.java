package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import io.swagger.annotations.*;

@Api(tags = "Pedidos")
public interface AtualizacaoPedidoControllerOpenApi {

    @ApiOperation("Confirma um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiProblem.class)
    })
    void confirmar(
            @ApiParam(value = "Código do pedido", example = "05d2b496-9751-4d4d-9875-71444271fc25", required = true)
            String codigoPedido
    );

    @ApiOperation("Registra a entrega de um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiProblem.class)
    })
    void entregar(
            @ApiParam(value = "Código do pedido", example = "05d2b496-9751-4d4d-9875-71444271fc25", required = true)
            String codigoPedido
    );

    @ApiOperation("Cancela um pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiProblem.class)
    })
    void cancelar(
            @ApiParam(value = "Código do pedido", example = "05d2b496-9751-4d4d-9875-71444271fc25", required = true)
            String codigoPedido
    );
}