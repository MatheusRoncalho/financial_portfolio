package com.roncalho.financial_portfolio.dto.in;

import com.roncalho.financial_portfolio.enums.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 1, max = 255, message = "A descrição deve ter entre 1 e 255 caracteres")
        String descricao,
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor,
        @NotNull(message = "Tipo de transação é obrigatório")
        TipoTransacao tipo,
        LocalDate dataTransacao,
        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId
) {
}
