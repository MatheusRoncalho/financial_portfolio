package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, JpaSpecificationExecutor<Transacao> {

    Optional<Transacao> findByIdAndUsuarioId(Long id, Long usuarioId);

}
