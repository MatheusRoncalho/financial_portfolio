package com.roncalho.financial_portfolio.dto.out;

import com.roncalho.financial_portfolio.model.Usuario;

import java.time.LocalDateTime;

public record RegisterResponseDTO(
        Long id,
        String username,
        String email,
        Boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
