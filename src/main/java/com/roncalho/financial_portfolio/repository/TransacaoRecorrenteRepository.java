package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransacaoRecorrenteRepository extends JpaRepository<TransacaoRecorrente, Long>, JpaSpecificationExecutor<TransacaoRecorrente> {

    Optional<TransacaoRecorrente> findByIdAndUsuarioId(Long id, Long usuarioId);

}

