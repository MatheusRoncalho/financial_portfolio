package com.roncalho.financial_portfolio.dto.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MetaResponseDTO(
        Long id,
        Long categoriaId,
        String categoriaNome,
        BigDecimal valorLimite,
        BigDecimal valorAtual,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal percentual,
        LocalDateTime criadoEm
) {
}
