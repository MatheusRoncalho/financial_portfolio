package com.roncalho.financial_portfolio.dto.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoRecorrenteResponseDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        String tipo,
        Long categoriaId,
        String categoriaNome,
        String periodo,
        String status,
        LocalDateTime dataInicial,
        LocalDateTime dataFinal,
        LocalDateTime proximaExecucao,
        LocalDateTime criadoEm
) {
}
