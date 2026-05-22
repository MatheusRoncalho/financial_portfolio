package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNome(String nome);

    @Query("SELECT c FROM Categoria c WHERE c.id = :id AND (c.usuario.id = :usuarioId OR (c.usuario IS NULL AND c.sistema = true))")
    Optional<Categoria> findByIdAndUsuarioId(Long id, Long usuarioId);

    @Query("SELECT c FROM Categoria c WHERE c.usuario.id = :usuarioId OR (c.usuario IS NULL AND c.sistema = true) ORDER BY c.id")
    List<Categoria> findByUsuarioId(Long usuarioId);

    Optional<Categoria> findByNomeAndUsuarioId(String nome, Long usuarioId);

    Page<Categoria> findByUsuarioIdOrSistemaIsTrue(Long usuarioId, Pageable pageable);
}