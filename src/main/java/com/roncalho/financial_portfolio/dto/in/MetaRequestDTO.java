package com.roncalho.financial_portfolio.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MetaRequestDTO(
        @NotNull(message = "A categoria é obrigatória") Long categoriaId,
        @NotNull(message = "O valor limite é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valorLimite,
        @NotBlank(message = "O período é obrigatório") String tipoPeriodo
) {
}
