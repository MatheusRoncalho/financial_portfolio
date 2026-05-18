package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Transacao;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Optional<Transacao> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Transacao> findByUsuarioId(Long usuarioId);

    List<Transacao> findByUsuarioIdAndDataTransacaoBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);

    List<Transacao> findByUsuarioIdAndCategoriaId(Long usuarioId, Long categoriaId);

    List<Transacao> findByUsuarioIdAndTipo(Long usuarioId, TipoTransacao tipo);

    List<Transacao> findByUsuarioIdAndCategoriaIdAndTipo(Long usuarioId, Long categoriaId, TipoTransacao tipo);
}
