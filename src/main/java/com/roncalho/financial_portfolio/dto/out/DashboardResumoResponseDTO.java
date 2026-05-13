package com.roncalho.financial_portfolio.dto.out;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DashboardResumoResponseDTO(
        BigDecimal saldoAtual,
        BigDecimal totalGanhos,
        BigDecimal totalGastos,
        BigDecimal saldoPeriodo,
        LocalDate dataInicio,
        LocalDate dataFim,
        Integer totalTransacoes,
        BigDecimal percentualGastos
) {
}
