package com.roncalho.financial_portfolio.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.validator.OperacaoValorObrigatoria;

import java.math.BigDecimal;
import java.time.LocalDate;

@OperacaoValorObrigatoria
public record TransacaoFiltroRequestDTO(
        Long categoriaId,
        String descricao,
        BigDecimal valor,
        OperacaoComparacao operacaoValor,
        TipoTransacao tipo,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate dataTransacao,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate dataFinal
) {
}
