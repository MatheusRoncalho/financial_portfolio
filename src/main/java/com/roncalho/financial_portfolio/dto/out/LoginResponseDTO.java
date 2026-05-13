package com.roncalho.financial_portfolio.dto.out;

import java.time.LocalDateTime;

public record LoginResponseDTO(
        String token,
        Long id,
        String username,
        String email,
        LocalDateTime geradoEm
) {
}
