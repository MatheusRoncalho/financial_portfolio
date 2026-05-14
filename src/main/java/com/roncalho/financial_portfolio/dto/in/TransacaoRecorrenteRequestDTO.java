package com.roncalho.financial_portfolio.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoRecorrenteRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 1, max = 255, message = "A descrição deve ter entre 1 e 255 caracteres")
        String descricao,
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor,
        @NotBlank(message = "Tipo de transação é obrigatório")
        String tipo,
        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId,
        @NotBlank(message = "Tipo do período é obrigatório")
        String tipoPeriodo,
        @NotNull(message = "Data inicial é obrigatória")
        LocalDateTime dataInicial,
        LocalDateTime dataFinal

) {
}
