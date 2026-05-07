package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import com.roncalho.financial_portfolio.model.StatusRecorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRecorrenteRepository extends JpaRepository<TransacaoRecorrente, Long> {

    List<TransacaoRecorrente> findByCategoriaId(Long categoriaId);

    List<TransacaoRecorrente> findByStatus(StatusRecorrencia status);

    List<TransacaoRecorrente> findByCategoriaIdAndStatus(Long categoriaId, StatusRecorrencia status);
}

