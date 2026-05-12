package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.out.DashboardResumoResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardService {

    // TODO: Implementar lógica completa de cálculo de totais baseado nas transações

    public DashboardResumoResponseDTO obterResumo(LocalDate dataInicio, LocalDate dataFim, Long usuarioId) {
        // Implementar cálculo de:
        // - saldoAtual
        // - totalGanhos
        // - totalGastos
        // - saldoPeriodo

        return new DashboardResumoResponseDTO();
    }

    public DashboardResumoResponseDTO obterResumoAtual(Long usuarioId) {
        return obterResumo(LocalDate.now().withDayOfMonth(1), LocalDate.now(), usuarioId);
    }
}

