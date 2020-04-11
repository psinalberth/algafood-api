package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Override
    @EntityGraph(attributePaths = { "grupos" })
    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);
}