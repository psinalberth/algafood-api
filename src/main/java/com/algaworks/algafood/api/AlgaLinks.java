package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import lombok.experimental.UtilityClass;
import org.springframework.hateoas.*;
import org.springframework.hateoas.TemplateVariable.VariableType;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@UtilityClass
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM)
    );

    // Cidade

    public static Link linkToCidades() {
       return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public static Link linkToCidades(String rel) {
        var urlCidades = linkTo(CidadeController.class).toUri().toString();
        return new Link(UriTemplate.of(urlCidades, PAGINACAO_VARIABLES), rel);
    }

    public static Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }
    
    // Cozinha

    public static Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public static Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public static Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToCozinha(Long cozinhaId, String rel) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId)).withRel(rel);
    }
    
    // Estado

    public static Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public static Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public static Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withRel(rel);
    }

    // Forma de Pagamento

    public static Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public static Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public static Link linkToFormaPagamento(Long formaPagamentoid) {
        return linkToFormaPagamento(formaPagamentoid, IanaLinkRelations.SELF.value());
    }

    public static Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null)).withRel(rel);
    }
    
    // Foto de Produto

    public static Link linkToFotoProduto(Long restauranteId, Long produtoId) {
        return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscar(restauranteId, produtoId)).withRel(rel);
    }

    // Grupo

    public static Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    public static Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class).withRel(rel);
    }

    public static Link linkToGrupo(Long grupoId) {
        return linkToGrupo(grupoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToGrupo(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoController.class)
                .buscar(grupoId)).withRel(rel);
    }

    public static Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToGrupoPermissoes(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .listar(grupoId)).withRel(rel);
    }

    public static Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .associar(grupoId, null)).withRel(rel);
    }

    public static Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .desassociar(grupoId, permissaoId)).withRel(rel);
    }

    // Pedido

    public static Link linkToPedidos(String rel) {
        var urlPedidos = linkTo(CadastroPedidoController.class).toUri().toString();
        var filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM)
        );

        return new Link(UriTemplate.of(urlPedidos, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public static Link linkToPedido(String codigoPedido) {
        return linkToPedido(codigoPedido, IanaLinkRelations.SELF.value());
    }

    public static Link linkToPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(CadastroPedidoController.class)
                .buscar(codigoPedido)).withRel(rel);
    }

    public static Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(AtualizacaoPedidoController.class)
                .confirmar(codigoPedido)).withRel(rel);
    }

    public static Link linkToEntregaPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(AtualizacaoPedidoController.class)
                .entregar(codigoPedido)).withRel(rel);
    }

    public static Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(AtualizacaoPedidoController.class)
                .cancelar(codigoPedido)).withRel(rel);
    }

    // Permissão

    public static Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }

    public static Link linkToPermissoes(String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }

    // Produto

    public static Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null)).withRel(rel);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId)).withRel(rel);
    }

    // Restaurante

    public static Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestaurantes(String rel) {
        var urlRestaurantes = linkTo(RestauranteController.class).toUri().toString();
        return new Link(UriTemplate.of(urlRestaurantes, PROJECAO_VARIABLES), rel);
    }

    public static Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .abrir(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .fechar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .ativar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .inativar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public static Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId)).withRel(rel);
    }

    public static Link linkToRestauranteResponsaveis(Long restauranteId) {
        return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public static Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .desassociar(restauranteId, usuarioId)).withRel(rel);
    }

    // Usuário

    public static Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public static Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public static Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(rel);
    }

    public static Link linkToUsuarioGrupos(Long usuarioId) {
        return linkToUsuarioGrupos(usuarioId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToUsuarioGrupos(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public static Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .associar(usuarioId, null)).withRel(rel);
    }

    public static Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .desassociar(usuarioId, grupoId)).withRel(rel);
    }
}