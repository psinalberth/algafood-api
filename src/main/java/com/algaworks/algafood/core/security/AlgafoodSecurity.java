package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgafoodSecurity {

    final RestauranteRepository restauranteRepository;
    final PedidoRepository pedidoRepository;

    public AlgafoodSecurity(RestauranteRepository restauranteRepository, PedidoRepository pedidoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        var jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean possuiPermissao(String permissao) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(permissao));
    }

    public boolean isAutenticado() {
        return getAuthentication().isAuthenticated();
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
    }

    // Permissões de leitura

    public boolean possuiEscopoLeitura() {
        return possuiPermissao("SCOPE_READ");
    }

    public boolean podeConsultarCidades() {
        return isAutenticado() && possuiEscopoLeitura();
    }

    public boolean podeConsultarCozinhas() {
        return isAutenticado() && possuiEscopoLeitura();
    }

    public boolean podeConsultarEstados() {
        return isAutenticado() && possuiEscopoLeitura();
    }

    public boolean podeConsultarEstatisticas() {
        return possuiEscopoLeitura() && possuiPermissao("GERAR_RELATORIOS");
    }

    public boolean podeConsultarFormasPagamento() {
        return isAutenticado() && possuiEscopoLeitura();
    }

    public boolean podePesquisarPedidos() {
        return isAutenticado() && possuiEscopoLeitura();
    }

    public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
        return possuiEscopoLeitura() && (possuiPermissao("CONSULTAR_PEDIDOS") ||
               usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
    }

    public boolean podeConsultarRestaurantes() {
        return isAutenticado() && possuiEscopoLeitura();
    }

    public boolean podeConsultarUsuariosGruposPermissoes() {
        return possuiEscopoLeitura() && possuiPermissao("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    // Permissões de escrita

    public boolean possuiEscopoEscrita() {
        return possuiPermissao("SCOPE_WRITE");
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {
        return possuiEscopoEscrita() && (possuiPermissao("GERENCIAR_PEDIDOS")
                || gerenciaRestauranteDoPedido(codigoPedido));
    }

    public boolean gerenciaRestaurante(Long restauranteId) {

        if (restauranteId == null)
            return false;

        return restauranteRepository.existsByResponsavel(restauranteId, getUsuarioId());
    }

    public boolean podeGerenciarCadastroRestaurantes() {
        return possuiEscopoEscrita() && possuiPermissao("EDITAR_RESTAURANTES");
    }

    public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
        return possuiEscopoEscrita() && (possuiPermissao("EDITAR_RESTAURANTES")
                || gerenciaRestaurante(restauranteId));
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    public boolean podeEditarUsuariosGruposPermissoes() {
        return possuiEscopoEscrita() && possuiPermissao("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }
}