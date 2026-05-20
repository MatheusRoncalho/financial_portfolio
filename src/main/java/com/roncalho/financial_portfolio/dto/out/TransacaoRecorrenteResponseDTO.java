package com.roncalho.financial_portfolio.dto.out;

import com.roncalho.financial_portfolio.enums.PeriodoRecorrencia;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransacaoRecorrenteResponseDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        Long categoriaId,
        String categoriaNome,
        PeriodoRecorrencia periodo,
        StatusRecorrencia status,
        LocalDate dataInicial,
        LocalDate dataFinal,
        LocalDate proximaExecucao,
        LocalDateTime criadoEm
) {
}
