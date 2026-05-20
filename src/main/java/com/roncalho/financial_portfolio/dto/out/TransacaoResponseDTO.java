package com.roncalho.financial_portfolio.dto.out;

import com.roncalho.financial_portfolio.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransacaoResponseDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        Long categoriaId,
        String categoriaNome,
        LocalDate dataTransacao,
        LocalDateTime criadoEm
) {
}
