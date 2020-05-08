package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.PedidoRequest;
import com.algaworks.algafood.api.model.response.PedidoResponse;
import com.algaworks.algafood.api.model.response.PedidoResumidoResponse;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Pedidos")
public interface CadastroPedidoControllerOpenApi {

    @ApiOperation("Lista os pedidos com paginação")
    PagedModel<PedidoResumidoResponse> listar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Código do pedido inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = ApiProblem.class)
    })
    PedidoResponse buscar(
            @ApiParam(value = "Código do pedido", example = "05d2b496-9751-4d4d-9875-71444271fc25", required = true)
            String codigoPedido
    );

    @ApiOperation("Cadastra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido cadastrado")
    })
    PedidoResponse salvar(
            @ApiParam(name = "corpo", value = "Representação de um pedido", required = true) PedidoRequest request
    );
}