package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Transacao;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Optional<Transacao> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Transacao> findByUsuarioId(Long usuarioId);

    Page<Transacao> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<Transacao> findByUsuarioIdAndDataTransacaoBetween(Long usuario_id, LocalDate dataTransacao, LocalDate dataTransacao2, Pageable pageable);

    List<Transacao> findByUsuarioIdAndCategoriaId(Long usuarioId, Long categoriaId);

    Page<Transacao> findByUsuarioIdAndCategoriaId(Long usuarioId, Long categoriaId, Pageable pageable);

    List<Transacao> findByUsuarioIdAndTipo(Long usuarioId, TipoTransacao tipo);

    Page<Transacao> findByUsuarioIdAndTipo(Long usuarioId, TipoTransacao tipo, Pageable pageable);

    List<Transacao> findByUsuarioIdAndCategoriaIdAndTipo(Long usuarioId, Long categoriaId, TipoTransacao tipo);

    Page<Transacao> findByUsuarioIdAndCategoriaIdAndTipo(Long usuarioId, Long categoriaId, TipoTransacao tipo, Pageable pageable);
}
