package com.roncalho.financial_portfolio.dto.out;

import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponseDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        Long categoriaId,
        String categoriaNome,
        LocalDateTime dataTransacao,
        LocalDateTime criadoEm
) {
}
