package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findByCategoriaId(Long categoriaId);

    Optional<Meta> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Meta> findByUsuarioId(Long usuarioId);

    List<Meta> findByCategoriaIdAndUsuarioId(Long categoriaId, Long usuarioId);
}

