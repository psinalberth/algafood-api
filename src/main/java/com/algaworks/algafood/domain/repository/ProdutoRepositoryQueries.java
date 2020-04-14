package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;

import java.util.Optional;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

    void delete(FotoProduto foto);
}