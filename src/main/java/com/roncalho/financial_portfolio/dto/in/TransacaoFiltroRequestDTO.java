package com.roncalho.financial_portfolio.dto.in;

import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoFiltroRequestDTO(
        String descricao,
        BigDecimal valor,
        OperacaoComparacao operacaoValor,
        TipoTransacao tipo,
        LocalDate dataTransacao,
        LocalDate dataFinal,
        Long categoria
) {
}
