package com.roncalho.financial_portfolio.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaRequestDTO(
        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId,
        @NotNull(message = "O valor limite é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valorLimite,
        @NotNull(message = "A data inicial é obrigatória")
        LocalDate dataInicio,
        @NotNull(message = "A data final é obrigatória")
        LocalDate dataFim

) {
}
