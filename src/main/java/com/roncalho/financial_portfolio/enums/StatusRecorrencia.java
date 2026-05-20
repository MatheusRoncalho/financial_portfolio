package com.roncalho.financial_portfolio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum StatusRecorrencia {
    ATIVO(1, "Ativo"),
    CANCELADO(2, "Cancelado");

    private final int valor;
    private final String descricao;

    public static StatusRecorrencia fromValor(int valor) {
        for (StatusRecorrencia status : values()) {
            if (status.getValor() == valor) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de status recorrência inválido: " + valor);
    }

    public static StatusRecorrencia fromTexto(String texto) {
        return switch (texto.toLowerCase()) {
            case "ativo", "ativado", "ligado", "true" -> ATIVO;
            case "cancelado", "inativo", "cancelada", "desligado", "false" -> CANCELADO;
            default -> throw new IllegalArgumentException("Status não reconhecido: " + texto);
        };
    }
}

