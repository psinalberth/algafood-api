package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Override
    @Query("select r from Restaurante r " +
            "join fetch r.cozinha " +
            "join fetch r.endereco.cidade c " +
            "join fetch c.estado " +
            "left join fetch r.responsaveis " +
            "left join fetch r.formasPagamento where r.id = :id")
    Optional<Restaurante> findById(@Param("id") Long id);

    boolean existsByResponsavel(Long restauranteId, Long usuarioId);
}