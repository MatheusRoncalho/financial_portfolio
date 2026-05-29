package com.roncalho.financial_portfolio.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.PeriodoRecorrencia;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.validator.DataValida;
import com.roncalho.financial_portfolio.validator.OperacaoValorObrigatoria;

import java.math.BigDecimal;
import java.time.LocalDate;

@OperacaoValorObrigatoria
@DataValida
public record TransacaoRecorrenteFiltroRequestDTO(
        Long categoriaId,
        String descricao,
        BigDecimal valor,
        OperacaoComparacao operacaoValor,
        TipoTransacao tipo,
        PeriodoRecorrencia periodo,
        StatusRecorrencia status,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate dataInicial,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        LocalDate dataFinal
) {
}
