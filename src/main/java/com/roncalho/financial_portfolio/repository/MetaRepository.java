package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findByCategoriaId(Long categoriaId);
}

