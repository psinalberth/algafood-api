package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgafoodSecurity {

    final RestauranteRepository restauranteRepository;

    public AlgafoodSecurity(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        var jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        return restauranteRepository.existsByResponsavel(restauranteId, getUsuarioId());
    }
}