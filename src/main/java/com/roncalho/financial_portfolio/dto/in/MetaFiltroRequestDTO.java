package com.roncalho.financial_portfolio.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaFiltroRequestDTO(
        Long categoriaId,
        BigDecimal valorLimite,
        OperacaoComparacao operacaoValor,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate dataInicio,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate dataFim
) {
}
