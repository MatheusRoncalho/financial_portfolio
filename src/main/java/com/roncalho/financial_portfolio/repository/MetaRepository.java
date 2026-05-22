package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.model.Meta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findByCategoriaId(Long categoriaId);

    Optional<Meta> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Meta> findByUsuarioId(Long usuarioId);

    Optional<Meta> findByCategoriaIdAndUsuarioId(Long categoriaId, Long usuarioId);

    @Query(value = """
    SELECT COALESCE(SUM(t.valor), 0)
    FROM transacao t
    WHERE t.usuario_id = :usuarioId
      AND t.categoria_id = :categoriaId
      AND t.tipo = 'SAIDA'
      AND t.data_transacao BETWEEN :dataInicio AND :dataFim
    """, nativeQuery = true)
    BigDecimal calcularTotalGastoNoPeriodo(
            @Param("categoriaId") Long categoriaId,
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("""
    SELECT m
    FROM Meta m
    WHERE m.categoria.id = :categoriaId
      AND m.usuario.id = :usuarioId
      AND :dataInicio <= m.dataFim
      AND :dataFim >= m.dataInicio
    """)
    Optional<Meta> buscarMetaConflitante(
            @Param("categoriaId") Long categoriaId,
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query(""" 
            SELECT
            m.id,
            m.categoria.id,
            m.categoria.nome,
            m.valorLimite,
            COALESCE(SUM(t.valor), 0),
            m.dataInicio,
            m.dataFim,
            ROUND((COALESCE(SUM(t.valor), 0) / m.valorLimite) * 100, 2),
            m.criadoEm
            FROM Meta m
            LEFT JOIN Transacao t ON t.categoria.id = m.categoria.id
              AND t.usuario.id = m.usuario.id
              AND t.tipo = 'SAIDA'
              AND t.dataTransacao BETWEEN m.dataInicio AND m.dataFim
            WHERE m.usuario.id = :usuarioId
            GROUP BY m.id, m.categoria.id, m.categoria.nome, m.valorLimite, m.dataInicio, m.dataFim, m.criadoEm
            """)
    Page<MetaResponseDTO> listarMetasComProgressoPaginado(@Param("usuarioId") Long usuarioId, Pageable pageable);
}

