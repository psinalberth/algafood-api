package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.ApiProblem;
import com.algaworks.algafood.api.model.request.AlteracaoSenhaRequest;
import com.algaworks.algafood.api.model.request.UsuarioRequest;
import com.algaworks.algafood.api.model.request.UsuarioSenhaRequest;
import com.algaworks.algafood.api.model.response.UsuarioResponse;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    List<UsuarioResponse> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = ApiProblem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiProblem.class)
    })
    UsuarioResponse buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId
    );

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    UsuarioResponse salvar(
            @ApiParam(name = "corpo", value = "Representação do usuário", required = true) UsuarioSenhaRequest request
    );

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiProblem.class)
    })
    UsuarioResponse atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação do usuário", required = true) UsuarioRequest request);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = ApiProblem.class)
    })
    void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação da nova senha", required = true)
            AlteracaoSenhaRequest request
    );
}