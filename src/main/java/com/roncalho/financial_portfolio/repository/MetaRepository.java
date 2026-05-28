package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.model.Meta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long>, JpaSpecificationExecutor<Meta> {

    Optional<Meta> findByIdAndUsuarioId(Long id, Long usuarioId);

    @Query(value = """
    SELECT COALESCE(SUM(t.valor), 0)
    FROM transacoes t
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

    @Query(value = """
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
}

