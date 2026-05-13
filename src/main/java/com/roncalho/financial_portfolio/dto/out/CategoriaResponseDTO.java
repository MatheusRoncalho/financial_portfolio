package com.roncalho.financial_portfolio.dto.out;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        Boolean sistema,
        LocalDateTime criadoEm
) {
}
