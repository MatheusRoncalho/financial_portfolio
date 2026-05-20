package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.Meta;
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

    List<Meta> findByCategoriaIdAndUsuarioId(Long categoriaId, Long usuarioId);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
            "WHERE t.categoria.id = :categoriaId " +
            "AND t.usuario.id = :usuarioId " +
            "AND t.tipo = 'SAIDA' " +
            "AND t.dataTransacao BETWEEN :dataInicio AND :dataFim")
    BigDecimal calcularTotalGastoNoPeriodo(
            @Param("categoriaId") Long categoriaId,
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);
}

