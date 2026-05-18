package com.roncalho.financial_portfolio.dto.out;

import com.roncalho.financial_portfolio.enums.PeriodoMeta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MetaResponseDTO(
        Long id,
        Long categoriaId,
        String categoriaNome,
        BigDecimal valorLimite,
        BigDecimal valorAtual,
        PeriodoMeta periodo,
        BigDecimal percentual,
        LocalDateTime criadoEm
) {
}
