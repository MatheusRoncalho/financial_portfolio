package com.roncalho.financial_portfolio.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Nome não pode ser vazio")
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
        String username,
        @NotBlank(message = "Email não pode ser vazio")
        @Email(message = "Email deve ser válido")
        String email,
        @NotBlank(message = "Senha não pode ser vazia")
        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
        String senha
) {
}
