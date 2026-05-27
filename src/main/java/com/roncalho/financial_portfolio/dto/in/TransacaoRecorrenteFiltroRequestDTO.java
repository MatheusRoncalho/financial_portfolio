package com.roncalho.financial_portfolio.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.PeriodoRecorrencia;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.enums.TipoTransacao;

import java.math.BigDecimal;

public record TransacaoRecorrenteFiltroRequestDTO(
        Long categoriaId,
        String descricao,
        BigDecimal valor,
        OperacaoComparacao operacaoValor,
        TipoTransacao tipo,
        PeriodoRecorrencia periodo,
        StatusRecorrencia status,
        @JsonFormat(pattern = "yyyy-MM-dd")
        String dataInicial,
        @JsonFormat(pattern = "yyyy-MM-dd")
        String dataFinal
) {
}
