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

        if (getAuthentication().getPrincipal() instanceof Jwt) {

            var jwt = (Jwt) getAuthentication().getPrincipal();
            return jwt.getClaim("user_id");
        }

        return 0L;
    }

    public boolean gerenciaRestaurante(Long restauranteId) {

        if (restauranteId == null)
            return false;

        return restauranteRepository.existsByResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }
}