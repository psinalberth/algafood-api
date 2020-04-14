package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Override
    public Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId) {
        String sql =
        "select f from FotoProduto f " +
                "join f.produto p " +
                "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId";

        try {
            FotoProduto fotoProduto = manager.createQuery(sql, FotoProduto.class)
                    .setParameter("restauranteId", restauranteId)
                    .setParameter("produtoId", produtoId)
                    .getSingleResult();

            return Optional.of(fotoProduto);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(FotoProduto foto) {
        manager.remove(foto);
    }
}