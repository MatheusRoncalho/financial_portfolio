package com.roncalho.financial_portfolio.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank(message = "O nome da categoria não pode ser vazio")
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
        String nome
) {
}
