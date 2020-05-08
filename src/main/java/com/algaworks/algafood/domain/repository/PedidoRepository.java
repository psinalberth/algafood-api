package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    @Query("select p from Pedido p " +
            "join fetch p.restaurante r " +
            "join fetch r.cozinha " +
            "join fetch p.cliente " +
            "join fetch p.itens " +
            "where p.codigo = :codigo")
    Optional<Pedido> findByCodigo(@Param("codigo") String codigo);

    boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);
}